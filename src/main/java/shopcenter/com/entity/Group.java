package shopcenter.com.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "group")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
	Integer groupId;
	
	@Column(name = "group_name")
	String groupName;
	
	@Column(name = "group_desc")
	String groupDesc;
	
	@OneToOne //để onetomany cũng được
	Role role;
	
	@Builder.Default
	@OneToMany(mappedBy = "group")
	Set<GroupHasUser> groups = new HashSet<>();
}
