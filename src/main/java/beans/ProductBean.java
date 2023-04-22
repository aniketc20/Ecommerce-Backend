package beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBean {
	private Integer productId;

	private String productName;
	private Integer qty;
	private Integer price;
	private String desc;
}
