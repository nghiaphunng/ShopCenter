package shopcenter.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shopcenter.com.core.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, CustomizedProductRepository{
	List<Product> findByUserUserId(Integer userId);
}
