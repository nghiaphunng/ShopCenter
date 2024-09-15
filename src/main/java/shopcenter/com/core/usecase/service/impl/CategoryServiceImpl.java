package shopcenter.com.core.usecase.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import shopcenter.com.core.entity.Category;
import shopcenter.com.framework.exception.AppException;
import shopcenter.com.framework.exception.ErrorCode;
import shopcenter.com.repository.CategoryRepository;
import shopcenter.com.adapter.dto.request.crud_category.CategoryRequest;
import shopcenter.com.adapter.dto.response.admin.crud_category.CategoryResponse;
import shopcenter.com.core.usecase.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
	public List<CategoryResponse> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream()
					.map(category -> modelMapper.map(category, CategoryResponse.class))
					.collect(Collectors.toList());
	}

	@Override
	@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
	public CategoryResponse createCategory(CategoryRequest categoryRequest) {
		List<Category> categories = categoryRepository.findByCategoryName(categoryRequest.getCategoryName());
		if(!categories.isEmpty()) throw new AppException(ErrorCode.CATEGORY_EXISTED);
		
		Category category = new Category();
		category.setCategoryName(categoryRequest.getCategoryName());
		
		categoryRepository.save(category);
		
		return modelMapper.map(category, CategoryResponse.class);
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
	@Override
	public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
		Category category = categoryRepository.findById(categoryRequest.getCategoryId())
								.orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
		
		category.setCategoryName(categoryRequest.getCategoryName());
		
		categoryRepository.save(category);
		
		return modelMapper.map(category, CategoryResponse.class);
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
		
		categoryRepository.delete(category);
	}

}
