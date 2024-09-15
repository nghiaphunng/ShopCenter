package shopcenter.com.adapter.dto.request.create_user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import shopcenter.com.framework.validator.DobConstraint;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotBlank(message = "vui lòng điền tên tài khoản")
    String userName;

    @NotBlank(message = "vui lòng điền địa chỉ")
    String userAddress;

    @Size(min = 6, message = "mật khẩu tối thiểu phải có 6 ký tự")
    @NotBlank(message = "vui lòng điền mật khẩu")
    String userPassword;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "vui lòng điền email")
    String userEmail;

    @NotBlank(message = "vui lòng điền đầy đủ họ tên")
    String userFullName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
}
