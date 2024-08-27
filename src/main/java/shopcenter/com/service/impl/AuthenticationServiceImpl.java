package shopcenter.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shopcenter.com.exception.AppException;
import shopcenter.com.exception.ErrorCode;
import shopcenter.com.repository.UserRepository;
import shopcenter.com.request.token.SignInRequest;
import shopcenter.com.response.token.TokenResponse;
import shopcenter.com.service.AuthenticationService;
import shopcenter.com.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public TokenResponse authenticate(SignInRequest signInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
		
		var user = userRepository.findByUserName(signInRequest.getUsername())
									.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		String accessToken = jwtService.generateToken(user);
		
		return TokenResponse.builder()
					.accessToken(accessToken)
					.refreshToken("refreshToken")
					.userId(user.getUserId())
					.build();
	}

}
