package shopcenter.com.core.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
	Integer userId;
	
	@Column(name = "user_name")
	String userName;
	
	@Column(name = "user_password")
	String userPassword;
	
	@Column(name = "user_email")
	String userEmail;
	
	@Column(name = "user_fullname")
	String userFullName;
	
	@Column(name = "user_address")
	String userAddress;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	List<Product> products = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonManagedReference
	List<Review> reviews = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_name")
	)
	private Set<Role> roles = new HashSet<>();
}
