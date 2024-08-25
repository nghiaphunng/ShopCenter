package shopcenter.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import shopcenter.com.request.create_product.CreateProductRequest;
import shopcenter.com.response.ApiResponse;
import shopcenter.com.response.create_product.CreateProductResponse;
import shopcenter.com.service.ProductService;

@Controller
@RequestMapping(value = {"/shop"})
public class ShopController {
	@Autowired
	private ProductService productService;
	
	@PostMapping("/create-product")
	public ApiResponse<?> createProduct(@RequestBody CreateProductRequest createProductRequest){
		CreateProductResponse result = productService.createProduct(createProductRequest);
		
		return ApiResponse.builder()
				.message("create product successfully")
				.result(result)
				.build();
	}
}
