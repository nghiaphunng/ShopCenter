package shopcenter.com.service;

import java.util.List;

import shopcenter.com.request.crud_category.CategoryRequest;
import shopcenter.com.response.crud_category.CategoryResponse;

public interface CategoryService {
	List<CategoryResponse> getAllCategories();
	CategoryResponse createCategory(CategoryRequest categoryRequest);
	CategoryResponse updateCategory(CategoryRequest categoryRequest);
	void deleteCategory(Integer categoryId);
}
