package shopcenter.com.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import shopcenter.com.entity.Product;
import shopcenter.com.repository.CustomizedProductRepository;

@Repository
public class CustomizedProductRepositoryImpl implements CustomizedProductRepository{
	@PersistenceContext
    private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> searchProductsByFilter(String keyword, Integer categoryId, Double minPrice, Double maxPrice,
			Double minRating, String color, String size, String stockStatus, Boolean sortByReviewCount) {
		StringBuilder sql = new StringBuilder("SELECT p.* FROM products p ");

        if (minPrice != null || maxPrice != null || stockStatus != null) {
            sql.append("JOIN product_variants pv ON p.product_id = pv.product_id ");
        }

        sql.append("WHERE 1=1 ");

        if (keyword != null && !keyword.isEmpty()) {
            sql.append("AND (p.product_name LIKE :keyword OR p.product_desc LIKE :keyword) ");
        }
        if (categoryId != null) {
            sql.append("AND p.category_id = :categoryId ");
        }
        if (minPrice != null && maxPrice != null) {
            sql.append("AND pv.price BETWEEN :minPrice AND :maxPrice ");
        } else if (minPrice != null) {
            sql.append("AND pv.price >= :minPrice ");
        } else if (maxPrice != null) {
            sql.append("AND pv.price <= :maxPrice ");
        }
        if (minRating != null) {
            sql.append("AND p.average_rating >= :minRating ");
        }
        if (color != null && !color.isEmpty()) {
            sql.append("AND EXISTS (SELECT 1 FROM variant_attributes va WHERE va.product_id = p.product_id AND va.attribute_name = 'Color' AND va.attribute_value = :color) ");
        }
        if (size != null && !size.isEmpty()) {
            sql.append("AND EXISTS (SELECT 1 FROM variant_attributes va WHERE va.product_id = p.product_id AND va.attribute_name = 'Size' AND va.attribute_value = :size) ");
        }
        if (stockStatus != null && !stockStatus.isEmpty()) {
            if ("InStock".equals(stockStatus)) {
                sql.append("AND pv.quantity > 0 ");
            } else if ("LowStock".equals(stockStatus)) {
                sql.append("AND pv.quantity <= 20 ");
            }
        }

        sql.append("ORDER BY p.product_name ASC");

        Query query = entityManager.createNativeQuery(sql.toString(), Product.class);

        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }
        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }
        if (minRating != null) {
            query.setParameter("minRating", minRating);
        }
        if (color != null && !color.isEmpty()) {
            query.setParameter("color", color);
        }
        if (size != null && !size.isEmpty()) {
            query.setParameter("size", size);
        }

        return query.getResultList();
	}
}
