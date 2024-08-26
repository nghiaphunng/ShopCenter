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
public class UpdateProductVariantResponse {
	Integer productVariantId;
    Double price;
    Integer quantity;
    List<UpdateVariantAttributeResponse> variantAttributes;
}
