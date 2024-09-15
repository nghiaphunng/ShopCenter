package shopcenter.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shopcenter.com.core.entity.User;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUserName(String userName);
	Optional<User> findByUserEmail(String userEmail);
}
