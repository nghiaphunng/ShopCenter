package shopcenter.com.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
	Integer productId;
	
	@Column(name = "product_name")
	String productName;
	
	@Column(name = "product_desc")
	String productDesc;
	
	@Column(name ="category")
	String category;
	
//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="user_id")
	@JsonBackReference
	User user;	
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	List<ProductVariant> productVariants = new ArrayList<>();
}

