package shopcenter.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shopcenter.com.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{	
	
}
