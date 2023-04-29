package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import org.json.JSONArray;
import org.json.JSONException;

import beans.OrderBean;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import repository.OrderRepository;

import models.OrderEntity;
import models.ProductOrder;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
    private CartService cartService;

	@Value("${jwt.secret}")
    private String SECRET_KEY;

	@Override
	public void addOrder(HttpServletRequest request) throws JSONException, IOException {
		// TODO Auto-generated method stub
		
		String userName = getUsernameFromToken(request);
		JSONObject obj = new JSONObject(request.getReader().lines().collect(Collectors.joining()));
		JSONArray products = obj.getJSONArray("prods");
		Set<ProductOrder> productOrders = new HashSet<ProductOrder>();
		Integer totalPrice = 0;
		for (int i = 0, size = products.length(); i < size; i++) {
			JSONObject objectInArray = products.getJSONObject(i);

			Integer productId = (Integer) objectInArray.get("productId");
			String productName = (String) objectInArray.get("product");
			Integer price = (Integer) objectInArray.get("price");
			String desc = (String) objectInArray.get("desc");
			Integer qty = (Integer) objectInArray.get("qty");
			String imgUrl = (String) objectInArray.get("imgUrl");
			totalPrice += price*qty;
			
			ProductOrder product = new ProductOrder(productId, productName, price, desc, qty, imgUrl);
			productOrders.add(product);
		}
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setProducts(productOrders);
		orderEntity.setTotalPrice(totalPrice);
		orderEntity.setUsername(userName);

		orderRepository.save(orderEntity);
		cartService.clearCart(request);
	}
	
	@Override
	public List<OrderBean> userOrders(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = getUsernameFromToken(request);
		List<OrderEntity> orderEntity = orderRepository.findByUsername(userName);
		List<OrderBean> orderBeans = new ArrayList<>();
        for (int i = 0; i < orderEntity.size(); i++) {
        	OrderBean orderBean = new OrderBean();
        	BeanUtils.copyProperties(orderEntity.get(i), orderBean);
            orderBeans.add(orderBean);
        }
		return orderBeans;
	}
	
	private String getUsernameFromToken(HttpServletRequest request) {
		String token = WebUtils.getCookie(request, "token").getValue();
		String userName = Jwts.parser()
				  .setSigningKey(SECRET_KEY)
				  .parseClaimsJws(token)
				  .getBody()
				  .getSubject();
		return userName;
	}
}
