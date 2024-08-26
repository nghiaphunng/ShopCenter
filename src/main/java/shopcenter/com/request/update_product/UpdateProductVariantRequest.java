package shopcenter.com.request.update_product;

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
public class UpdateProductVariantRequest {
	Integer variantId;
    private Double price;
    private Integer quantity;
    private List<UpdateVariantAttributeRequest> variantAttributes;
}
