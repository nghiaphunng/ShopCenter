package shopcenter.com.repository;

import java.util.List;

import shopcenter.com.core.entity.Product;

public interface CustomizedProductRepository {
	List<Product> searchProductsByFilter(String keyword, Integer categoryId, Double minPrice, Double maxPrice,
            Double minRating, String color, String size, String stockStatus,
            Boolean sortByReviewCount);
}
