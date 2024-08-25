package shopcenter.com.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shopcenter.com.entity.Product;
import shopcenter.com.entity.ProductVariant;
import shopcenter.com.entity.User;
import shopcenter.com.entity.VariantAttribute;
import shopcenter.com.request.create_product.CreateProductRequest;
import shopcenter.com.request.create_product.CreateProductVariantRequest;
import shopcenter.com.request.create_product.CreateVariantAttributeRequest;
import shopcenter.com.response.ProductInfoResponse;
import shopcenter.com.response.ProductResponse;
import shopcenter.com.response.ProductVariantResponse;
import shopcenter.com.response.UserResponse;
import shopcenter.com.response.VariantAttributeResponse;
import shopcenter.com.response.create_product.CreateProductResponse;
import shopcenter.com.response.create_product.CreateProductVariantResponse;
import shopcenter.com.response.create_product.CreateVariantAttributeResponse;

@Configuration
public class ModelMapperConfig {
	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		//chuyển đổi User -> UserResponse(thông tin người bán) 
		modelMapper.typeMap(User.class, UserResponse.class).addMappings(mapper -> {
			mapper.map(User::getUserId, UserResponse::setUserId);
			mapper.map(User::getUserName, UserResponse::setUserName);
			mapper.map(User::getUserEmail, UserResponse::setUserEmail);
		});
		
		//chuyển đổi Product -> ProductResponse
		modelMapper.typeMap(Product.class, ProductResponse.class).addMappings(mapper -> {
			mapper.map(Product::getProductId, ProductResponse::setProductId);
			mapper.map(Product::getProductName, ProductResponse::setProductName);
			mapper.map(Product::getProductDesc, ProductResponse::setProductDesc);
			mapper.map(Product::getCategory, ProductResponse::setCategory);
			mapper.map(src -> src.getProductVariants(), ProductResponse::setProductVariantResponses);
		});
		
		//chuyển đổi ProductVariant -> ProductVariantResponse
		modelMapper.typeMap(ProductVariant.class, ProductVariantResponse.class).addMappings(mapper -> {
			mapper.map(ProductVariant::getVariantId, ProductVariantResponse::setProductVariantId);
			mapper.map(ProductVariant::getPrice, ProductVariantResponse::setPrice);
			mapper.map(ProductVariant::getQuantity, ProductVariantResponse::setQuantity);
			mapper.map(src -> src.getVariantAttributes(), ProductVariantResponse::setVariantAttributeResponses);
		});	
		
		
		//chuyển đổi VariantAttribute -> VariantAttributeResponse
		 modelMapper.typeMap(VariantAttribute.class, VariantAttributeResponse.class).addMappings(mapper -> {
	        mapper.map(VariantAttribute::getAttributeId, VariantAttributeResponse::setAttributeId);
	        mapper.map(VariantAttribute::getAttributeName, VariantAttributeResponse::setAttributeName);
	        mapper.map(VariantAttribute::getAttributeValue, VariantAttributeResponse::setAttributeValue);
		});
		 
		//chuyển đổi Proudct -> ProductInfoResponse (thực hiện chức năng getAll)
		 modelMapper.typeMap(Product.class, ProductInfoResponse.class).addMappings(mapper -> {
			mapper.map(src -> src.getUser().getUserId(), ProductInfoResponse::setShopId);
			mapper.map(src -> src.getUser().getUserName(), ProductInfoResponse::setShopName);
			mapper.map(Product::getProductId, ProductInfoResponse::setProductId);
			mapper.map(Product::getProductId, ProductInfoResponse::setProductId);
			mapper.map(Product::getProductName, ProductInfoResponse::setProductName);
			mapper.map(Product::getProductDesc, ProductInfoResponse::setProductDesc);
			mapper.map(Product::getCategory, ProductInfoResponse::setCategory);
			mapper.map(src -> src.getProductVariants(), ProductInfoResponse::setProductVariantResponses);
			});
		 
		// Chuyển đổi Product -> CreateProductResponse
        modelMapper.typeMap(Product.class, CreateProductResponse.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUserId(), CreateProductResponse::setShopId);
            mapper.map(src -> src.getUser().getUserName(), CreateProductResponse::setShopName);
            mapper.map(Product::getProductId, CreateProductResponse::setProductId);
            mapper.map(Product::getProductName, CreateProductResponse::setProductName);
            mapper.map(Product::getProductDesc, CreateProductResponse::setProductDesc);
            mapper.map(Product::getCategory, CreateProductResponse::setCategory);
            mapper.map(Product::getProductVariants, CreateProductResponse::setProductVariants);
        });

        // Mapping ProductVariant -> CreateProductVariantResponse
        modelMapper.typeMap(ProductVariant.class, CreateProductVariantResponse.class).addMappings(mapper -> {
            mapper.map(ProductVariant::getVariantId, CreateProductVariantResponse::setProductVariantId);
            mapper.map(ProductVariant::getPrice, CreateProductVariantResponse::setPrice);
            mapper.map(ProductVariant::getQuantity, CreateProductVariantResponse::setQuantity);
            mapper.map(ProductVariant::getVariantAttributes, CreateProductVariantResponse::setVariantAttributes);
        });

        // Mapping VariantAttribute -> CreateVariantAttributeResponse
        modelMapper.typeMap(VariantAttribute.class, CreateVariantAttributeResponse.class).addMappings(mapper -> {
            mapper.map(VariantAttribute::getAttributeName, CreateVariantAttributeResponse::setAttributeName);
            mapper.map(VariantAttribute::getAttributeValue, CreateVariantAttributeResponse::setAttributeValue);
        });
	    
        //chuyển đổi CreateProductRequest -> Product
        modelMapper.typeMap(CreateProductRequest.class, Product.class).addMappings(mapper -> {
        	mapper.map(CreateProductRequest::getCategory, Product::setCategory);
        	mapper.map(CreateProductRequest::getProductDesc, Product::setProductDesc);
        	mapper.map(CreateProductRequest::getProductName, Product::setProductName);
        	mapper.map(CreateProductRequest::getProductVariants, Product::setProductVariants);
        });
        
        //chuyển đổi CreateProductVariantRequest -> ProductVariant
        modelMapper.typeMap(CreateProductVariantRequest.class, ProductVariant.class).addMappings(mapper -> {
        	mapper.map(CreateProductVariantRequest::getPrice, ProductVariant::setPrice);
        	mapper.map(CreateProductVariantRequest::getQuantity, ProductVariant::setQuantity);
        	mapper.map(CreateProductVariantRequest::getVariantAttributes, ProductVariant::setVariantAttributes);
        });
        
        
        //chuyển đổi CreateVariantAttributeRequest -> VariantAttribute
        modelMapper.typeMap(CreateVariantAttributeRequest.class, VariantAttribute.class).addMappings(mapper -> {
        	mapper.map(CreateVariantAttributeRequest::getAttributeName, VariantAttribute::setAttributeName);
        	mapper.map(CreateVariantAttributeRequest::getAttributeValue, VariantAttribute::setAttributeValue);
        });
        
		return modelMapper;
	}
}
