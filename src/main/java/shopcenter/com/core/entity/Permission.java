package shopcenter.com.core.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
    @Id
    @Column(name = "permission_name")
    String permissionName;

    @Column(name = "permission_desc")
    String permissionDesc;

    @ManyToMany(mappedBy = "permissions")
    Set<Role> roles = new HashSet<>();
}
