package shopcenter.com.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import shopcenter.com.response.ApiResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = RuntimeException.class)
	ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException exception){
		ApiResponse<?> apiResponse = new ApiResponse<>();
		apiResponse.setCode(1001);
		apiResponse.setMessage(exception.getMessage());
		
		return ResponseEntity.badRequest().body(apiResponse);
	}
	
	@ExceptionHandler(value = AppException.class)
	ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception){
		ErrorCode errorCode = exception.getErrorCode();
		
		ApiResponse<?> apiResponse = new ApiResponse<>();
		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());
		
		return ResponseEntity
				.status(errorCode.getStatusCode())
				.body(apiResponse);
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception){
		ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
		
		return ResponseEntity
				.status(errorCode.getStatusCode())
				.body(ApiResponse.builder()
						.code(errorCode.getCode())
						.message(errorCode.getMessage())
						.build()
					);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception){
		String enumKey = exception.getFieldError().getDefaultMessage();
		ErrorCode errorCode = ErrorCode.valueOf(enumKey);
		
		ApiResponse<?> apiResponse = new ApiResponse<>();
		apiResponse.setCode(errorCode.getCode());
		apiResponse.setMessage(errorCode.getMessage());
		
		return ResponseEntity.badRequest().body(apiResponse);
	}
}
