package shopcenter.com.adapter.dto.request.update_user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String userName;
    String userAddress;
    String userPassword;
    String userEmail;
    String userFullName;
    Set<String> roles;
}
