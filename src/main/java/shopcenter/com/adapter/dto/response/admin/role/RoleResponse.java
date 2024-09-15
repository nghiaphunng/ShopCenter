package shopcenter.com.adapter.dto.response.admin.role;

import lombok.*;
import lombok.experimental.FieldDefaults;
import shopcenter.com.adapter.dto.response.admin.permission.PermissionResponse;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String roleName;
    String roleDesc;
    Set<PermissionResponse> permissions;
}
