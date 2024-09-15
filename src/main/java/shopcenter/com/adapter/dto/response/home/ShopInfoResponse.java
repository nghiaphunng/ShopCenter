package shopcenter.com.adapter.dto.response.home;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ShopInfoResponse {
	UserResponse shop; //thông tin người bán
	List<ProductResponse> products; //danh sách thông tin sản phẩm của người bán
}
