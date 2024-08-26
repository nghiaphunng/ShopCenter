package shopcenter.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	USER_NAME_EXISTED(1001, "username existed", HttpStatus.BAD_REQUEST),
	USER_EMAIL_EXISTED(1002, "email existed", HttpStatus.BAD_REQUEST),
	USER_NAME_INVALID(1003, "please create username", HttpStatus.BAD_REQUEST),
	USER_PASSWORD_INVALID(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
	USER_EMAIL_INVALID(1005, "Please fill in email information", HttpStatus.BAD_REQUEST),
	USER_NOT_EXISTED(1006, "user not existed", HttpStatus.NOT_FOUND),
	SHOP_NOT_EXISTED(1007, "shop not existed", HttpStatus.NOT_FOUND),
	PRODUCT_NOT_EXISTED(1008, "product not existed", HttpStatus.NOT_FOUND),
	USER_EMAIL_NOT_EXISTED(1009, "useremail not existed", HttpStatus.NOT_FOUND),
	UNAUTHENTICATED(1010, "unauthenticated", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1011, "you do not have permission", HttpStatus.FORBIDDEN)
	
	;
	
	ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}
	
	private int code;
	private String message;
	private HttpStatusCode statusCode;
}
