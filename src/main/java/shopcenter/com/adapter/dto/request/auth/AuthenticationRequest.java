package shopcenter.com.adapter.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotBlank(message = "vui lòng nhập tên tài khoản")
    String userName;
    @NotBlank(message = "vui lòng nhập mật khẩu")
    String userPassword;
}
