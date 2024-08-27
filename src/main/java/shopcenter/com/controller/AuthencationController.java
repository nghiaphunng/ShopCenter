package shopcenter.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shopcenter.com.request.token.SignInRequest;
import shopcenter.com.response.token.TokenResponse;
import shopcenter.com.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthencationController {
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/access") //đăng nhập
	public ResponseEntity<TokenResponse> login(@RequestBody SignInRequest signInRequest) {
		return new ResponseEntity<>(authenticationService.authenticate(signInRequest), HttpStatus.OK);
	}
}
