package shopcenter.com.adapter.dto.response.detail_product;

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
public class ReviewProductDetailResponse {
	Integer userId;
	String userFullName;
	Integer rating;
	String comment;
	String updatedAt;
}
