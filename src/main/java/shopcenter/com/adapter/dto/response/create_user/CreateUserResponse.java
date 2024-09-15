package shopcenter.com.adapter.dto.response.create_user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserResponse {
    String userId;
    String userName;
    String userAddress;
    String userEmail;
    String userFullName;
}
