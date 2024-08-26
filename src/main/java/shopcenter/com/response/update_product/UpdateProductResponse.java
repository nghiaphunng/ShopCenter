package shopcenter.com.response.update_product;

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
public class UpdateProductResponse {
	Integer productId;
    String productName;
    String productDesc;
    String category;
    Integer shopId;
    String shopName;
    List<UpdateProductVariantResponse> productVariants;
}
