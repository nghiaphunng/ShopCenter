package shopcenter.com.core.usecase.service;

import shopcenter.com.adapter.dto.request.create_review.CreateReviewRequest;
import shopcenter.com.adapter.dto.request.update_review.UpdateReviewRequest;
import shopcenter.com.adapter.dto.response.create_review.CreateReviewResponse;
import shopcenter.com.adapter.dto.response.update_review.UpdateReviewResponse;

public interface ReviewService {
	CreateReviewResponse createReview(CreateReviewRequest createReviewRequest);
	UpdateReviewResponse updateReview(UpdateReviewRequest updateReviewRequest);
}
