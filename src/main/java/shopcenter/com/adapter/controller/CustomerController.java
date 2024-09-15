package shopcenter.com.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import shopcenter.com.adapter.dto.request.create_review.CreateReviewRequest;
import shopcenter.com.adapter.dto.request.update_review.UpdateReviewRequest;
import shopcenter.com.adapter.dto.response.ApiResponse;
import shopcenter.com.adapter.dto.response.create_review.CreateReviewResponse;
import shopcenter.com.adapter.dto.response.detail_product.ProductDetailResponse;
import shopcenter.com.adapter.dto.response.update_review.UpdateReviewResponse;
import shopcenter.com.core.usecase.service.ProductService;
import shopcenter.com.core.usecase.service.ReviewService;

@RestController
public class CustomerController {
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/shop/product/{id}")
	public ApiResponse<?> getProductById(@PathVariable(name = "id") Integer productId){
		ProductDetailResponse productDetailResponse = productService.getProductById(productId);
		return ApiResponse.builder()
				.message("get product detail successfully")
				.result(productDetailResponse)
				.build();
	}
	
	@PostMapping("/shop/review/create")
	public ApiResponse<?> createReview(@RequestBody CreateReviewRequest createReviewRequest){
		CreateReviewResponse result = reviewService.createReview(createReviewRequest);
		return ApiResponse.builder()
				.message("add review successfully")
				.result(result)
				.build();
	}
	
	@PutMapping("/shop/review/update")
	public ApiResponse<?> updateReview(@RequestBody UpdateReviewRequest updateReviewRequest){
		UpdateReviewResponse result = reviewService.updateReview(updateReviewRequest);
		return ApiResponse.builder()
				.message("update review successfully")
				.result(result)
				.build();
	}
}
