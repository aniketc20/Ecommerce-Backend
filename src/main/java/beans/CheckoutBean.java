package beans;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutBean {
	private HashMap<Integer, Integer> prods;
	private Integer cost;
}
