package beans;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import models.ProductOrder;

@Getter
@Setter
public class CartBean {
	private Set<ProductOrder> products;
	private String username;
}
