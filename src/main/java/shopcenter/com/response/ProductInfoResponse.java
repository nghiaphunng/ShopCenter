package shopcenter.com.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductInfoResponse {
	//thông tin riêng lẻ của mỗi sản phẩm
	String shopName;
	Integer shopId;
	Integer productId;
	String productName;
	String productDesc;
	String category;
	List<ProductVariantResponse> productVariantResponses;
}
