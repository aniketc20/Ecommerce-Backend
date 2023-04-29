package controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONException;

import beans.CartBean;
import beans.OrderBean;
import jakarta.servlet.http.HttpServletRequest;

import service.CartService;
import service.OrderService;


@RestController
@CrossOrigin(origins = {"${spring.datasource.apiUrl}", "http://localhost:3000", "http://127.0.0.1:3000"}, allowCredentials = "true", allowedHeaders = "*")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	@GetMapping("/orders")
	public List<OrderBean> dashboard(HttpServletRequest request) {
		List<OrderBean> orders = orderService.userOrders(request);
		return orders;
	}

	@PostMapping("/buy")
	public String checkout(HttpServletRequest request) throws IOException, JSONException {
		orderService.addOrder(request);
		return "success";
	}

	@PostMapping("/addToCart")
	public CartBean addToCart(@RequestBody Integer pId, HttpServletRequest request) {
		return cartService.addToCart(pId, request);
	}

	@PostMapping("/decreaseQty")
	public CartBean decreaseItemQty(@RequestBody Integer pId, HttpServletRequest request) {
		return cartService.decreaseQty(pId, request);
	}

	@GetMapping("/getCart")
	public CartBean cart(HttpServletRequest request) {
		return cartService.getCart(request);
	}

	@PostMapping("/removeItem")
	public CartBean deleteItemFromCart(@RequestBody Integer pId, HttpServletRequest request) {
		return cartService.removeItemFromCart(pId, request);
	}
}
