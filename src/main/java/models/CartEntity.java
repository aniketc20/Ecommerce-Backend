package models;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class CartEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cartId;
	private String username;
	
	@ElementCollection
	@CollectionTable(name = "user_cart", joinColumns = @JoinColumn(name = "cart_id"))
    private Set<ProductOrder> products;
}
