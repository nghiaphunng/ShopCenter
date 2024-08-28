package shopcenter.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import shopcenter.com.request.token.ResetPasswordDTO;
import shopcenter.com.request.token.SignInRequest;
import shopcenter.com.response.token.TokenResponse;
import shopcenter.com.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthencationController {
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/access-token") //đăng nhập
	public ResponseEntity<TokenResponse> login(@RequestBody SignInRequest signInRequest) {
		return new ResponseEntity<>(authenticationService.accessToken(signInRequest), HttpStatus.OK);
	}
	
	@PostMapping("/refresh-token") //tạo token mới khi access token hết hạn
	public ResponseEntity<TokenResponse> refresh(HttpServletRequest request) {		
		return new ResponseEntity<>(authenticationService.refreshToken(request), HttpStatus.OK);
	}
	
	@PostMapping("/remove-token") //đăng xuất
	public ResponseEntity<String> logout(HttpServletRequest request) {		
		return new ResponseEntity<>(authenticationService.removeToken(request), HttpStatus.OK);
	}
	
	@PostMapping("/forgot-password") //lấy lại mật khẩu
	public ResponseEntity<String> forgotPassword(@RequestBody String email) {		
		return new ResponseEntity<>(authenticationService.forgotPassword(email), HttpStatus.OK);
	}
	
	@PostMapping("/reset-password") 
	public ResponseEntity<String> resetPassword(@RequestBody String secretKey) { // resetToken
		return new ResponseEntity<>(authenticationService.resetPassword(secretKey), HttpStatus.OK);
	}
	
	@PostMapping("/change-password") 
	public ResponseEntity<String> changePassword(@RequestBody ResetPasswordDTO resetPasswordDTO) { 
		return new ResponseEntity<>(authenticationService.changePassword(resetPasswordDTO), HttpStatus.OK);
	}
}
