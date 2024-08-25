package shopcenter.com.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shopcenter.com.entity.Product;
import shopcenter.com.entity.ProductVariant;
import shopcenter.com.entity.User;
import shopcenter.com.entity.VariantAttribute;
import shopcenter.com.exception.AppException;
import shopcenter.com.exception.ErrorCode;
import shopcenter.com.repository.ProductRepository;
import shopcenter.com.repository.UserRepository;
import shopcenter.com.request.create_product.CreateProductRequest;
import shopcenter.com.response.ProductInfoResponse;
import shopcenter.com.response.ProductResponse;
import shopcenter.com.response.ShopInfoResponse;
import shopcenter.com.response.UserResponse;
import shopcenter.com.response.create_product.CreateProductResponse;
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

}
