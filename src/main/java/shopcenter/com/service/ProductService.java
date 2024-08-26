package shopcenter.com.service;

import java.util.List;

import shopcenter.com.request.create_product.CreateProductRequest;
import shopcenter.com.request.update_product.UpdateProductRequest;
import shopcenter.com.response.ProductInfoResponse;
import shopcenter.com.response.ShopInfoResponse;
import shopcenter.com.response.create_product.CreateProductResponse;
import shopcenter.com.response.update_product.UpdateProductResponse;

public interface ProductService {
	ShopInfoResponse getProductsByShopId(Integer userId);
	List<ProductInfoResponse> getAllProducts();
	CreateProductResponse createProduct(CreateProductRequest createProductRequest);
	UpdateProductResponse updateProduct(UpdateProductRequest updateProductRequest);
	void deleteProduct(Integer productId);
}
