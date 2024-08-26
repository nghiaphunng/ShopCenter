package shopcenter.com.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import shopcenter.com.entity.Product;
import shopcenter.com.entity.ProductVariant;
import shopcenter.com.entity.User;
import shopcenter.com.entity.VariantAttribute;
import shopcenter.com.exception.AppException;
import shopcenter.com.exception.ErrorCode;
import shopcenter.com.repository.ProductRepository;
import shopcenter.com.repository.UserRepository;
import shopcenter.com.request.create_product.CreateProductRequest;
import shopcenter.com.request.update_product.UpdateProductRequest;
import shopcenter.com.request.update_product.UpdateProductVariantRequest;
import shopcenter.com.request.update_product.UpdateVariantAttributeRequest;
import shopcenter.com.response.ProductInfoResponse;
import shopcenter.com.response.ProductResponse;
import shopcenter.com.response.ShopInfoResponse;
import shopcenter.com.response.UserResponse;
import shopcenter.com.response.create_product.CreateProductResponse;
import shopcenter.com.response.update_product.UpdateProductResponse;
import shopcenter.com.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ShopInfoResponse getProductsByShopId(Integer userId) {
		User shop = userRepository.findById(userId)
						.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		List<Product> products = productRepository.findByUserUserId(userId);
		
		UserResponse infoShop = modelMapper.map(shop, UserResponse.class);
		List<ProductResponse> productResponses = products.stream()	
				.map(product -> modelMapper.map(product, ProductResponse.class))
				.collect(Collectors.toList());
		
		return ShopInfoResponse.builder()
				.shop(infoShop)
				.products(productResponses)
				.build();
	}

	@Override
	public List<ProductInfoResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream()
					.map(product -> modelMapper.map(product, ProductInfoResponse.class))
					.collect(Collectors.toList());
	}

	@Override
	public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
		User shop = userRepository.findById(createProductRequest.getShopId())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		
		Product product = modelMapper.map(createProductRequest, Product.class);
		product.setUser(shop);
		
		for(ProductVariant variant : product.getProductVariants()) {
			variant.setProduct(product);
			for(VariantAttribute attribute : variant.getVariantAttributes()) {
				attribute.setProductVariant(variant);
			}
		}
				
		Product saveProduct = productRepository.save(product);
		
		return modelMapper.map(saveProduct, CreateProductResponse.class);
	}

	@Override
	@Transactional
	public UpdateProductResponse updateProduct(UpdateProductRequest updateProductRequest) {
		Product product = productRepository.findById(updateProductRequest.getProductId())
				.orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
		
		//cập nhật các thuộc tính 
		product.setCategory(updateProductRequest.getCategory());
		product.setProductName(updateProductRequest.getProductName());
		product.setProductDesc(updateProductRequest.getProductDesc());
		
		//xóa để nhập nhật lại theo put
		product.getProductVariants().clear();
		
		for(UpdateProductVariantRequest variantRequest : updateProductRequest.getProductVariants()) {
			ProductVariant variant = new ProductVariant();
			variant.setPrice(variantRequest.getPrice());
			variant.setQuantity(variantRequest.getQuantity());
			variant.setProduct(product);			
			product.getProductVariants().add(variant);
			
			for(UpdateVariantAttributeRequest attributeRequest : variantRequest.getVariantAttributes()) {
				VariantAttribute attribute = new VariantAttribute();
				attribute.setAttributeName(attributeRequest.getAttributeName());
				attribute.setAttributeValue(attributeRequest.getAttributeValue());
				attribute.setProductVariant(variant);
				
				variant.getVariantAttributes().add(attribute);
			}
		}
		
		productRepository.save(product);
		
		return modelMapper.map(product, UpdateProductResponse.class);
	}

	@Override
	public void deleteProduct(Integer productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
		
		productRepository.delete(product);
	}

}
