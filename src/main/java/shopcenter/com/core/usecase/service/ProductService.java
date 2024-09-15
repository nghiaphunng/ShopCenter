package shopcenter.com.core.usecase.service;

import java.util.List;

import shopcenter.com.adapter.dto.request.create_product.CreateProductRequest;
import shopcenter.com.adapter.dto.request.update_product.UpdateProductRequest;
import shopcenter.com.adapter.dto.response.home.ProductInfoResponse;
import shopcenter.com.adapter.dto.response.home.ShopInfoResponse;
import shopcenter.com.adapter.dto.response.create_product.CreateProductResponse;
import shopcenter.com.adapter.dto.response.detail_product.ProductDetailResponse;
import shopcenter.com.adapter.dto.response.update_product.UpdateProductResponse;

public interface ProductService {
	ShopInfoResponse getProductsByShopId(Integer userId);
	List<ProductInfoResponse> getAllProducts();
	CreateProductResponse createProduct(CreateProductRequest createProductRequest);
	UpdateProductResponse updateProduct(UpdateProductRequest updateProductRequest);
	void deleteProduct(Integer productId);
	ProductDetailResponse getProductById(Integer productId);
	public List<ProductInfoResponse> getProductsByFilter(String keyword, Integer categoryId, Double minPrice,
			Double maxPrice, Double minRating, String color, String size, String stockStatus, Boolean sortByReviewCount);
}
