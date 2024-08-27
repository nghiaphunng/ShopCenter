package shopcenter.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shopcenter.com.request.crud_category.CategoryRequest;
import shopcenter.com.response.ApiResponse;
import shopcenter.com.response.crud_category.CategoryResponse;
import shopcenter.com.service.CategoryService;

@RestController
@RequestMapping(value ={"/admin"})
public class AdminController {
	@Autowired
	private CategoryService categoryService;
	
	//crud category 
	@GetMapping("/category")
	public ApiResponse<?> getAllCategories(){
		List<CategoryResponse> categoryResponses = categoryService.getAllCategories();
		return ApiResponse.builder()
				.message("get info all category successfully")
				.result(categoryResponses)
				.build();
	}
	
	@PostMapping("/category/create")
	public ApiResponse<?> createCategory(@RequestBody CategoryRequest categoryRequest){
		CategoryResponse result = categoryService.createCategory(categoryRequest);
		return ApiResponse.builder()
				.message("create category successfully")
				.result(result)
				.build();
	}
	
	@PutMapping("/category/update")
	public ApiResponse<?> updateCategory(@RequestBody CategoryRequest categoryRequest){
		CategoryResponse result = categoryService.updateCategory(categoryRequest);
		return ApiResponse.builder()
				.message("delete category successfully")
				.result(result)
				.build();
	}
	
	@DeleteMapping("/category/delete/{id}")
	public ApiResponse<?> deleteCategory(@PathVariable(name = "id") Integer categoryId){
		categoryService.deleteCategory(categoryId);
		return ApiResponse.builder()
				.message("delete category successfully")
				.build();
	}
}
