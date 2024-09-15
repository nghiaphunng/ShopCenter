package shopcenter.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shopcenter.com.core.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

}
