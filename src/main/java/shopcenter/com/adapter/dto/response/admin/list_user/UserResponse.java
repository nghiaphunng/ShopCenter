package shopcenter.com.adapter.dto.response.admin.list_user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userId;
    String userName;
    String userFullName;
    String userEmail;
    String userAddress;
    Set<String> roles;
}
