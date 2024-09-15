package shopcenter.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopcenter.com.core.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(String roleName);
}
