package shopcenter.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shopcenter.com.entity.Review;
import shopcenter.com.entity.Product;
import java.util.List;
import shopcenter.com.entity.User;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	List<Review> findByUserAndProduct(User user, Product product);
}
