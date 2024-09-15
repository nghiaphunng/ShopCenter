package shopcenter.com.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "variant_attributes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantAttribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
	Integer attributeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "variant_id")
	@JsonIgnore 
	ProductVariant productVariant;
	
	@Column(name = "attribute_name")
	String attributeName;
	
	@Column(name = "attribute_value")
	String attributeValue;
}
