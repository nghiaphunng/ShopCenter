package shopcenter.com.core.usecase.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shopcenter.com.core.entity.Product;
import shopcenter.com.core.entity.Review;
import shopcenter.com.core.entity.User;
import shopcenter.com.framework.exception.AppException;
import shopcenter.com.framework.exception.ErrorCode;
import shopcenter.com.repository.ProductRepository;
import shopcenter.com.repository.ReviewRepository;
import shopcenter.com.repository.UserRepository;
import shopcenter.com.adapter.dto.request.create_review.CreateReviewRequest;
import shopcenter.com.adapter.dto.request.update_review.UpdateReviewRequest;
import shopcenter.com.adapter.dto.response.create_review.CreateReviewResponse;
import shopcenter.com.adapter.dto.response.update_review.UpdateReviewResponse;
import shopcenter.com.core.usecase.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CreateReviewResponse createReview(CreateReviewRequest createReviewRequest) {
		User user = userRepository.findById(createReviewRequest.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
        Product product = productRepository.findById(createReviewRequest.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        
        List<Review> reviews = reviewRepository.findByUserAndProduct(user, product);
        if(!reviews.isEmpty()) throw new AppException(ErrorCode.REVIEW_EXISTED);
        
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setComment(createReviewRequest.getComment());
        review.setRating(createReviewRequest.getRating());
        review.setCreatedAt(LocalDateTime.now());
        
        reviewRepository.save(review);
        
		return modelMapper.map(review, CreateReviewResponse.class);
	}

	@Override
	public UpdateReviewResponse updateReview(UpdateReviewRequest updateReviewRequest) {
		Review review = reviewRepository.findById(updateReviewRequest.getReviewId())
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_EXISTED));
		
		if(!updateReviewRequest.getUserId().equals(review.getUser().getUserId())) 
				throw new AppException(ErrorCode.USER_NOT_AUTHORIZED_REVIEW);
		
		review.setRating(updateReviewRequest.getRating());
		review.setComment(updateReviewRequest.getComment());
		review.setUpdatedAt(LocalDateTime.now());
		
		reviewRepository.save(review);
		
		return modelMapper.map(review, UpdateReviewResponse.class);
	}
}
