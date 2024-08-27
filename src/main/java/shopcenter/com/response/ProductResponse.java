package shopcenter.com.response;

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
public class ProductResponse {
	Integer productId;
	String productName;
	String productDesc;
	Integer reviewCount;
	Double averageRating;
	CategoryResponse categoryResponse;
//	List<ProductVariantResponse> productVariantResponses;
}
