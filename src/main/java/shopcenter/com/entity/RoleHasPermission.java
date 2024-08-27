package shopcenter.com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_has_permission")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleHasPermission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_has_permission_id")
	Integer roleHasPermissionId;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	Role role;
	
	@ManyToOne
	@JoinColumn(name = "permission_id")
	Permission permission;
}
