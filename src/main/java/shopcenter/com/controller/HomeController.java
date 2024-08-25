package shopcenter.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shopcenter.com.response.ApiResponse;
import shopcenter.com.response.ProductInfoResponse;
import shopcenter.com.response.ShopInfoResponse;
import shopcenter.com.service.ProductService;

@Controller
@RequestMapping(value = {"/home"})
public class HomeController {
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = {"/", ""})
	@ResponseBody
	public ApiResponse<?> getAllProducts(){
		List<ProductInfoResponse> productInfoResponses = productService.getAllProducts();
		return ApiResponse.builder()
				.message("get info all product successfully")
				.result(productInfoResponses)
				.build();
	}
	
	@GetMapping("/shop/{id}")
	@ResponseBody
	public ApiResponse<?> getProductsByShopId(@PathVariable(name = "id") Integer userId){
		ShopInfoResponse shopInfoResponse = productService.getProductsByShopId(userId);
		return ApiResponse.builder()
					.message("get info product of shop successfully")
					.result(shopInfoResponse)
					.build();
	}
}
