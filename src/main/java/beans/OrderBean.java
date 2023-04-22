package beans;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import models.ProductOrder;

@Getter
@Setter
public class OrderBean {
	private Integer orderId;
	
	private String username;
	private Integer totalPrice;
	private List<ProductOrder> products;
}
