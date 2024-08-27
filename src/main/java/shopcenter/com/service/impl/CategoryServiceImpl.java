package shopcenter.com.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shopcenter.com.entity.Category;
import shopcenter.com.exception.AppException;
import shopcenter.com.exception.ErrorCode;
import shopcenter.com.repository.CategoryRepository;
import shopcenter.com.request.crud_category.CategoryRequest;
import shopcenter.com.response.crud_category.CategoryResponse;
import shopcenter.com.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<CategoryResponse> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream()
					.map(category -> modelMapper.map(category, CategoryResponse.class))
					.collect(Collectors.toList());
	}

	@Override
	public CategoryResponse createCategory(CategoryRequest categoryRequest) {
		List<Category> categories = categoryRepository.findByCategoryName(categoryRequest.getCategoryName());
		if(!categories.isEmpty()) throw new AppException(ErrorCode.CATEGORY_EXISTED);
		
		Category category = new Category();
		category.setCategoryName(categoryRequest.getCategoryName());
		
		categoryRepository.save(category);
		
		return modelMapper.map(category, CategoryResponse.class);
	}

	@Override
	public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
		Category category = categoryRepository.findById(categoryRequest.getCategoryId())
								.orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
		
		category.setCategoryName(categoryRequest.getCategoryName());
		
		categoryRepository.save(category);
		
		return modelMapper.map(category, CategoryResponse.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
		
		categoryRepository.delete(category);
	}

}
