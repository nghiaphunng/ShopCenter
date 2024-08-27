package shopcenter.com.response.detail_product;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import shopcenter.com.response.CategoryResponse;
import shopcenter.com.response.ProductVariantResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
	//trả về thông tin chi tiết 1 sản phẩm
	Integer productId;
	Integer shopId;
	String shopName;
	String shopAddress;
	String productName;
	String productDesc;
	Integer reviewCount;
	Double averageRating;
	CategoryResponse categoryResponse;
	List<ProductVariantResponse> productVariantResponses;
	List<ReviewProductDetailResponse> reviews;
}
