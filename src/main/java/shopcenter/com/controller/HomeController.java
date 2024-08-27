package shopcenter.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shopcenter.com.response.ApiResponse;
import shopcenter.com.response.ProductInfoResponse;
import shopcenter.com.response.ShopInfoResponse;
import shopcenter.com.service.ProductService;

@RestController
@RequestMapping(value = {"/home"})
public class HomeController {
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = {"/", ""})
	public ApiResponse<?> getAllProducts(){
		List<ProductInfoResponse> productInfoResponses = productService.getAllProducts();
		return ApiResponse.builder()
				.message("get info all product successfully")
				.result(productInfoResponses)
				.build();
	}
	
	@GetMapping("/shop/{id}")
	public ApiResponse<?> getProductsByShopId(@PathVariable(name = "id") Integer userId){
		ShopInfoResponse shopInfoResponse = productService.getProductsByShopId(userId);
		return ApiResponse.builder()
					.message("get info product of shop successfully")
					.result(shopInfoResponse)
					.build();
	}
	
	@GetMapping(value = "/search")
	public ApiResponse<?> getProductsBySearch(@RequestParam(required = false, value = "keyword") String keyword,
								            @RequestParam(required = false, value = "categoryId") Integer categoryId,
								            @RequestParam(required = false, value = "minPrice") Double minPrice,
								            @RequestParam(required = false, value = "maxPrice") Double maxPrice,
								            @RequestParam(required = false, value = "minRating") Double minRating,
								            @RequestParam(required = false, value = "color") String color,
								            @RequestParam(required = false, value = "size") String size,
								            @RequestParam(required = false, value = "stockStatus") String stockStatus,
								            @RequestParam(required = false, value = "sortByReviewCount", defaultValue = "false") Boolean sortByReviewCount){
		List<ProductInfoResponse> productsSearch = productService.getProductsByFilter(keyword, categoryId, minPrice, maxPrice, minRating, color, size, stockStatus, sortByReviewCount);
		return ApiResponse.builder()
				.message("get search product successfully")
				.result(productsSearch)
				.build();
	}
}
