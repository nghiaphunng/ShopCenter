package shopcenter.com.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
	Integer roleId;
	
	@Column(name = "role_name")
	String roleName;
	
	@Column(name = "role_desc")
	String roleDesc;
	
	@Builder.Default
	@OneToMany(mappedBy = "role")
	Set<RoleHasPermission> permissions = new HashSet<>();
	
	@Builder.Default
	@OneToMany(mappedBy = "role")
	Set<UserHasRole> roles = new HashSet<>();
}
