package shopcenter.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shopcenter.com.core.entity.Review;
import shopcenter.com.core.entity.Product;
import java.util.List;
import shopcenter.com.core.entity.User;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	List<Review> findByUserAndProduct(User user, Product product);
}
