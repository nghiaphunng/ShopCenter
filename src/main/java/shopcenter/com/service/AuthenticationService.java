package shopcenter.com.service;

import shopcenter.com.request.token.SignInRequest;
import shopcenter.com.response.token.TokenResponse;

public interface AuthenticationService {
	TokenResponse authenticate(SignInRequest signInRequest);
}
