package shopcenter.com.framework.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Common API error response format")
public class ErrorResponse {
    @ApiModelProperty(value = "Timestamp of the error response", example = "2024-09-09T12:34:56.789")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @ApiModelProperty(value = "HTTP status code", example = "400")
    private int status;

    @ApiModelProperty(value = "Path where the error occurred", example = "/api/v1/users")
    private String path;

    @ApiModelProperty(value = "Error code", example = "1001")
    private int errorCode;

    @ApiModelProperty(value = "Error message", example = "Username existed")
    private String message;

    public ErrorResponse(int value, int code, String message, String replace) {
    }
}
