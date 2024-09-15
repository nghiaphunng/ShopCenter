package shopcenter.com.framework.exception;

import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
////import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.context.request.WebRequest;
import shopcenter.com.adapter.dto.response.ApiResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final String MIN_ATTRIBUTE = "min";

	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e, WebRequest request){
		String enumKey = e.getFieldError().getDefaultMessage();

		ErrorCode errorCode = ErrorCode.INVALID_KEY;

		Map<String, Object> attributes = null;

		try{
			errorCode = ErrorCode.valueOf(enumKey);

			var constrainValidation = e.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
			attributes = constrainValidation.getConstraintDescriptor().getAttributes();

			log.info(attributes.toString());


		} catch (IllegalArgumentException exception){

		}

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
		errorResponse.setErrorCode(1000); //mã lỗi cụ thể cho validate

		errorResponse.setMessage(Objects.nonNull(attributes) ? mapAttribute(errorCode.getMessage(), attributes) : errorCode.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AppException.class)
	public ResponseEntity<ErrorResponse> handleAppException(AppException e, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(
				e.getErrorCode().getStatusCode().value(),
				e.getErrorCode().getCode(),
				e.getErrorCode().getMessage(),
				request.getDescription(false).replace("uri=", "") // Đường dẫn API
		);

		return new ResponseEntity<>(errorResponse, e.getErrorCode().getStatusCode());
	}

	private String mapAttribute(String message, Map<String, Object> attributes){
		String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

		return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
	}
}
