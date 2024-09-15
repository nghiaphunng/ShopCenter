package shopcenter.com.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Common API response format")
public class ResponseData<T> {
    @ApiModelProperty(value = "Timestamp of the response", example = "2024-09-09T12:34:56.789")
    private LocalDateTime timestamp;

    @ApiModelProperty(value = "HTTP status code", example = "200")
    private HttpStatus status;

    @ApiModelProperty(value = "Response message", example = "Request successful")
    private String message;

    @ApiModelProperty(value = "Response data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    //PUT PATCH DELETE
    public ResponseData(HttpStatus status, String message){
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    //POST GET
    public ResponseData(HttpStatus status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
