package shopcenter.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shopcenter.com.core.entity.Category;
import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	List<Category> findByCategoryName(String categoryName);
}
