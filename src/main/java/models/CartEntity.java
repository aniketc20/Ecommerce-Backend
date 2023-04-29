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
	
	/*
	 * This is a helper table named "user_cart"
	 * No DB operations can be done in this table
	 * It has a FK relation with CartEntity joined with it's P.K "cart_id"
	 * The parent table "cart" assigns a unique "cartId" to each user one time.
	 * This table adds a row each time the user adds a product to their cart.
	 *  **Note: If the product is already present in the user's cart, it increments
	 * that product's "qty" field by +1**
	 * This is essentially a One to Many relationship with the parent table "cart"
	 * Product Order is Embeddeble here
	 */
	@ElementCollection
	@CollectionTable(name = "user_cart", joinColumns = @JoinColumn(name = "cart_id"))
    private Set<ProductOrder> products;
}
