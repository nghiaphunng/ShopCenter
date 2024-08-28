package shopcenter.com.service;

import jakarta.servlet.http.HttpServletRequest;
import shopcenter.com.request.token.ResetPasswordDTO;
import shopcenter.com.request.token.SignInRequest;
import shopcenter.com.response.token.TokenResponse;

public interface AuthenticationService {
	TokenResponse accessToken(SignInRequest signInRequest);
	TokenResponse refreshToken(HttpServletRequest request);
	String removeToken(HttpServletRequest request);
	String forgotPassword(String email);
	String resetPassword(String secretKey);
	String changePassword(ResetPasswordDTO resetPasswordDTO);
}
