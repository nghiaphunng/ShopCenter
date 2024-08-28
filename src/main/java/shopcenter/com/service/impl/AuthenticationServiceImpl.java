package shopcenter.com.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shopcenter.com.entity.Token;
import shopcenter.com.entity.User;
import shopcenter.com.enums.TokenType;
import shopcenter.com.exception.AppException;
import shopcenter.com.exception.ErrorCode;
import shopcenter.com.repository.UserRepository;
import shopcenter.com.request.token.ResetPasswordDTO;
import shopcenter.com.request.token.SignInRequest;
import shopcenter.com.response.token.TokenResponse;
import shopcenter.com.service.AuthenticationService;
import shopcenter.com.service.JwtService;
import shopcenter.com.service.TokenService;
import shopcenter.com.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private TokenService tokenService;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	@Override
	public TokenResponse accessToken(SignInRequest signInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
		
		var user = userRepository.findByUserName(signInRequest.getUsername())
									.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		String accessToken = jwtService.generateToken(user);
		
		String refreshToken = jwtService.generateRefreshToken(user);
		
		//lưu token vào DB
		Token token = Token.builder()
						.username(user.getUsername())
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
		
		tokenService.save(token);
		
		return TokenResponse.builder()
					.accessToken(accessToken)
					.refreshToken(refreshToken)
					.userId(user.getUserId())
					.build();
	}

	@Override
	public TokenResponse refreshToken(HttpServletRequest request) {
		//validate
		String refreshToken = request.getHeader("x-token");
		
		if(StringUtils.isBlank(refreshToken)) {
			throw new AppException(ErrorCode.TOKEN_BLANK);
		}
		
		//extract user từ token
		final String userName = jwtService.extractUsername(refreshToken, TokenType.REFRESH_TOKEN);
//		System.out.println("Username: " + userName);
		
		//check it into DB
		Optional<User> user = userRepository.findByUserName(userName);
//		System.out.println("Username: " + user.get().getUserId());
		
		//check token có hợp lệ
		if(!jwtService.isValid(refreshToken, TokenType.REFRESH_TOKEN, user.get())) {
			throw new AppException(ErrorCode.TOKEN_INVALID);
		}
		
		String accessToken = jwtService.generateToken(user.get());
		
		//không cần tạo refreshToken, nếu hết hiệu lực phải login lại
		
		return TokenResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.userId(user.get().getUserId())
				.build();
	}

	@Override
	public String removeToken(HttpServletRequest request) {
		//validate
		String refreshToken = request.getHeader("x-token");
		
		if(StringUtils.isBlank(refreshToken)) {
			throw new AppException(ErrorCode.TOKEN_BLANK);
		}
		
		//extract user từ token
		final String userName = jwtService.extractUsername(refreshToken, TokenType.ACCESS_TOKEN);
		
		//check token in DB
		Token currentToken = tokenService.getByUsername(userName);
		
		return tokenService.delete(currentToken);
	}
	
	@Override
	public String forgotPassword(String email) {
		//check email có trong DB
		User user = userService.getUserByEmail(email);
		if(!user.isEnabled()) throw new AppException(ErrorCode.USER_NOT_ACTIVE);
				
		//tạo reset token 
		String resetToken = jwtService.generateResetToken(user);
		
		//gửi email confirmLink
		String confirmLink = String.format("curl --location 'localhost:8085/auth/reset-password' \\\r\n"
				+ "--header 'accept: */*' \\\r\n"
				+ "--header 'Content-Type: text/plain' \\\r\n"
				+ "--data '%s'", resetToken);
		
		log.info("confirmLink: {}", confirmLink);
		
		return "Sent";
	}
	
	@Override
	public String resetPassword(String secretKey) {
		log.info("----------resetPassword----------");
		
		isValidUserByToken(secretKey);
		
		return "reset";
	}
	
	@Override
	public String changePassword(ResetPasswordDTO resetPasswordDTO) {
		User user = isValidUserByToken(resetPasswordDTO.getSecretKey());
		
		if(!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())) {
			throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
		}
		
		user.setUserPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));	
		
		userRepository.save(user);
		
		
		return "Changed";
	}
	
	private User isValidUserByToken(String secretKey) {
		//extract user từ token
		final String userName = jwtService.extractUsername(secretKey, TokenType.RESET_TOKEN);
		
		//check it into DB
		Optional<User> user = userRepository.findByUserName(userName);
		
		if(!user.get().isEnabled()) throw new AppException(ErrorCode.USER_NOT_ACTIVE);
		
		//check token có hợp lệ
		if(!jwtService.isValid(secretKey, TokenType.RESET_TOKEN, user.get())) {
			throw new AppException(ErrorCode.TOKEN_INVALID);
		}
		
		return user.get();
	}
}
