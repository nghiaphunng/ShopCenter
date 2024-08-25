package shopcenter.com.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "product_variants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
	Integer variantId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="product_id")
	@JsonBackReference
	Product product;
	
	@Column(name ="price")
	Double price;
	
	@Column(name = "quantity")
	Integer quantity;
	
	@OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<VariantAttribute> variantAttributes = new ArrayList<>();
}

