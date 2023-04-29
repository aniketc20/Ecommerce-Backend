package models;

import java.util.List;
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
@Table(name = "order")
@Getter
@Setter
public class OrderEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer orderId;
	
	private String username;
	private Integer totalPrice;
	
	/*
	 * This is a helper table named "user_orders"
	 * No DB operations can be done in this table
	 * It has a FK relation with OrderEntity joined with it's P.K "order_id"
	 * This is essentially a One to Many relationship with the parent table "order"
	 * Product Order is Embeddeble here
	 */
	@ElementCollection
	@CollectionTable(name = "user_orders", joinColumns = @JoinColumn(name = "order_id"))
	private Set<ProductOrder> products;
	
}
