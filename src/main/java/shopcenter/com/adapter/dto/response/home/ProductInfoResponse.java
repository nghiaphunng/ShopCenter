package shopcenter.com.adapter.dto.response.home;

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
	Integer reviewCount;
	Double averageRating;
	CategoryResponse categoryResponse;
//	List<ProductVariantResponse> productVariantResponses;
}
