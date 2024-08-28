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
	CATEGORY_NOT_EXISTED(1009, "product not existed", HttpStatus.NOT_FOUND),
	CATEGORY_EXISTED(1009, "product not existed", HttpStatus.BAD_REQUEST),
	REVIEW_EXISTED(1010, "review existed", HttpStatus.BAD_REQUEST),
	REVIEW_NOT_EXISTED(1011, "review not existed", HttpStatus.NOT_FOUND),
	USER_NOT_AUTHORIZED_REVIEW(1012, "user does not have permission to edit review", HttpStatus.NOT_FOUND),
	USER_EMAIL_NOT_EXISTED(1013, "useremail not existed", HttpStatus.NOT_FOUND),
	UNAUTHENTICATED(1014, "unauthenticated", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(1015, "you do not have permission", HttpStatus.FORBIDDEN),
	TOKEN_BLANK(1016, "token must be not blank", HttpStatus.BAD_REQUEST),
	TOKEN_INVALID(1017, "token is invalid", HttpStatus.BAD_REQUEST),
	TOKEN_NOT_EXISTED(1018, "token not exists", HttpStatus.NOT_FOUND),
	PASSWORD_NOT_MATCH(1019, "password not match", HttpStatus.BAD_REQUEST),
	TOKEN_TYPE_INVALID(1020, "token type invalid", HttpStatus.BAD_REQUEST),
	USER_NOT_ACTIVE(1021, "user not active", HttpStatus.BAD_REQUEST)
	
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
