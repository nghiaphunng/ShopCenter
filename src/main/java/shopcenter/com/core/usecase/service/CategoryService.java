package shopcenter.com.core.usecase.service;

import java.util.List;

import shopcenter.com.adapter.dto.request.crud_category.CategoryRequest;
import shopcenter.com.adapter.dto.response.admin.crud_category.CategoryResponse;

public interface CategoryService {
	List<CategoryResponse> getAllCategories();
	CategoryResponse createCategory(CategoryRequest categoryRequest);
	CategoryResponse updateCategory(CategoryRequest categoryRequest);
	void deleteCategory(Integer categoryId);
}
