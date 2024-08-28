package shopcenter.com.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "token")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
	Integer tokenId;
	
	@Column(name = "username", unique = true)
	String username;
	
	@Column(name = "access_token")
	String accessToken;
	
	@Column(name = "refresh_token")
	String refreshToken;
	
	@Column(name = "created_at")
	@CreationTimestamp
	Date createdAt;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	Date updatedAt;
}
