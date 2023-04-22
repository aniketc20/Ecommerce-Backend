package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import beans.CartBean;
import beans.CheckoutBean;
import beans.OrderBean;

import models.ProductOrder;

import service.CartService;
import service.OrderService;
import service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService prodService;

	@Autowired
	private CartService cartService;

	@GetMapping("/orders")
	public List<OrderBean> dashboard(Model model) {
		String user = "aniket";
		List<OrderBean> orders = orderService.findByUserName(user);
		model.addAttribute("orders", orderService.findByUserName(user));
		return orders;
	}

	@PostMapping("/buy")
	public String checkout(@RequestBody CheckoutBean checkoutBean) {
		List<ProductOrder> products = new ArrayList<>();
		for (Integer i : checkoutBean.getProds().keySet()) {
			int prodId = i;
			String productName = prodService.getProduct(i).getProductName();
			int cost = prodService.getProduct(i).getPrice();
			String desc = prodService.getProduct(i).getDesc();
			Integer qty = checkoutBean.getProds().get(i);
			ProductOrder product = new ProductOrder(prodId, productName, cost, desc, qty);
			products.add(product);
		}
		OrderBean orderBean = new OrderBean();
		orderBean.setProducts(products);
		orderBean.setTotalPrice(checkoutBean.getCost());
		orderBean.setUsername("aniket");
		orderService.addOrder(orderBean);
		return "success";
	}

	@PostMapping("/addToCart")
	public CartBean addToCart(@RequestBody Integer pId) {
		return cartService.addToCart(pId);
	}

	@PostMapping("/decreaseQty")
	public CartBean decreaseItemQty(@RequestBody Integer pId) {
		return cartService.decreaseQty(pId);
	}

	@GetMapping("/getCart")
	public CartBean cart() {
		return cartService.getCart("aniket");
	}

	@PostMapping("/removeItem")
	public CartBean deleteItemFromCart(@RequestBody Integer pId) {
		return cartService.removeItemFromCart(pId);
	}
}
