package shopcenter.com.core.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @Column(name = "role_name")
    String roleName;

    @Column(name = "role_desc")
    String roleDesc;

    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "role_permission",
        joinColumns = @JoinColumn(name = "role_name"),
        inverseJoinColumns = @JoinColumn(name = "permission_name")
    )
    Set<Permission> permissions = new HashSet<>();
}
