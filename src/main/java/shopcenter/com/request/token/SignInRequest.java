package shopcenter.com.request.token;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import shopcenter.com.enums.Platform;

@Getter
public class SignInRequest {
	@NotBlank(message = "username must be not null")
	private String username;
	
	@NotBlank(message = "password must be not null")
	private String password;
	
	@NotNull(message = "platform must be not null")
	private Platform platform;
	
	private String version;
	
	private String deviceToken;
}
