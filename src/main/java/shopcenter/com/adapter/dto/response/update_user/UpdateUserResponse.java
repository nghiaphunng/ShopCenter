package shopcenter.com.adapter.dto.response.update_user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import shopcenter.com.adapter.dto.response.admin.role.RoleResponse;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserResponse {
    Integer userId;
    String userName;
    String userAddress;
    String userEmail;
    String userFullName;
    Set<RoleResponse> roles;
}
