package shopcenter.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shopcenter.com.entity.User;
import java.util.Optional;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUserName(String userName);
	Optional<User> findByUserEmail(String userEmail);
}
