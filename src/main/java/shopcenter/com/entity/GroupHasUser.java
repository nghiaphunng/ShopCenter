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
@Table(name = "group_has_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupHasUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_has_user_id")
	Integer groupHasUserId;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	Group group;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;	
}
