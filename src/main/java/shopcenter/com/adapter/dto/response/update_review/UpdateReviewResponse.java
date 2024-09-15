package shopcenter.com.adapter.dto.response.update_review;

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
public class UpdateReviewResponse {
	Integer reviewId;
	String userFullName;
	String productName;
	Integer rating;
	String comment;
	String createdDate;
	String updatedDate;
}
