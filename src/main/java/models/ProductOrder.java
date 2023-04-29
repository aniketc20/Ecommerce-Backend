package models;

import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ProductOrder {
	
	private Integer productId;
	private String product;
	private Integer price;
	private String desc;
	private Integer qty;
	private String imgUrl;

	public ProductOrder(Integer productId, String product, Integer price, String desc, Integer qty, String imgUrl) {
		super();
		this.productId = productId;
		this.product = product;
		this.price = price;
		this.desc = desc;
		this.qty = qty;
		this.imgUrl = imgUrl;
	}

	public ProductOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

}
