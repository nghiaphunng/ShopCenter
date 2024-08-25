package shopcenter.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shopcenter.com.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
