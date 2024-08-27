package shopcenter.com.service;

import shopcenter.com.request.create_review.CreateReviewRequest;
import shopcenter.com.request.update_review.UpdateReviewRequest;
import shopcenter.com.response.create_review.CreateReviewResponse;
import shopcenter.com.response.update_review.UpdateReviewResponse;

public interface ReviewService {
	CreateReviewResponse createReview(CreateReviewRequest createReviewRequest);
	UpdateReviewResponse updateReview(UpdateReviewRequest updateReviewRequest);
}
