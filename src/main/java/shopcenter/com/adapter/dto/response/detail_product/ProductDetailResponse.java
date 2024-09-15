package shopcenter.com.adapter.dto.response.detail_product;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import shopcenter.com.adapter.dto.response.home.CategoryResponse;
import shopcenter.com.adapter.dto.response.home.ProductVariantResponse;

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
