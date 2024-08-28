package shopcenter.com.request.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordDTO {
	String secretKey;
	String password;
	String confirmPassword;
}
