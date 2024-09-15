package shopcenter.com.adapter.controller;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopcenter.com.adapter.dto.request.auth.AuthenticationRequest;
import shopcenter.com.adapter.dto.request.auth.IntrospectRequest;
import shopcenter.com.adapter.dto.request.auth.LogoutRequest;
import shopcenter.com.adapter.dto.request.auth.RefreshTokenRequest;
import shopcenter.com.adapter.dto.response.ApiResponse;
import shopcenter.com.adapter.dto.response.auth.AuthenticationResponse;
import shopcenter.com.adapter.dto.response.auth.IntrospectResponse;
import shopcenter.com.core.usecase.service.AuthenticationService;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthencationController {
	@Autowired
	private AuthenticationService authenticationService;

	//đăng nhập
	@PostMapping("/login")
	ApiResponse<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request){
		var result = authenticationService.authenticate(request);
		return ApiResponse.<AuthenticationResponse>builder()
				.result(result)
				.build();
	}

	//xác thực token
	@PostMapping("/introspect")
	ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
		var result = authenticationService.introspect(request);
		return ApiResponse.<IntrospectResponse>builder()
				.result(result)
				.build();
	}

	//refresh token
	@PostMapping("/refresh")
	ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
		var result = authenticationService.refreshToken(request);
		return ApiResponse.<AuthenticationResponse>builder()
				.result(result)
				.build();
	}

	//đăng xuất
	@PostMapping("/logout")
	ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
		authenticationService.logout(request);
		return ApiResponse.<Void>builder()
				.build();
	}
}
