package beans;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import models.ProductOrder;

@Getter
@Setter
public class OrderBean {
	private Integer orderId;
	
	private String username;
	private Integer totalPrice;
	private Set<ProductOrder> products;
}
