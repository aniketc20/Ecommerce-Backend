package models;

import java.util.List;

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
import java.util.ArrayList;

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
	
	@ElementCollection
	@CollectionTable(name = "user_orders", joinColumns = @JoinColumn(name = "order_id"))
	private List<ProductOrder> products = new ArrayList<>();
	
}
