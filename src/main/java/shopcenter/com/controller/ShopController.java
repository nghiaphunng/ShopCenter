package shopcenter.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shopcenter.com.request.create_product.CreateProductRequest;
import shopcenter.com.request.update_product.UpdateProductRequest;
import shopcenter.com.response.ApiResponse;
import shopcenter.com.response.create_product.CreateProductResponse;
import shopcenter.com.response.update_product.UpdateProductResponse;
import shopcenter.com.service.ProductService;

@RestController
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
	
	@PutMapping("update-product")
	public ApiResponse<?> updateProduct(@RequestBody UpdateProductRequest updateProductRequest){
		UpdateProductResponse result = productService.updateProduct(updateProductRequest);
		
		return ApiResponse.builder()
				.message("update product successfully")
				.result(result)
				.build();
	}
	
	@DeleteMapping("delete-product/{id}")
	public ApiResponse<?> deleteProduct(@PathVariable(name = "id") Integer productId){
		productService.deleteProduct(productId);
		
		return ApiResponse.builder()
				.message("delete product successfully")
				.build();
	}
}