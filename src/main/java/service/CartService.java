package service;

import beans.CartBean;
import jakarta.servlet.http.HttpServletRequest;

public interface CartService {
	public CartBean addToCart(Integer productId, HttpServletRequest request);
	public CartBean getCart(HttpServletRequest request);
	public void clearCart(HttpServletRequest request);
	public CartBean removeItemFromCart(Integer productId, HttpServletRequest request);
	public CartBean decreaseQty(Integer productId, HttpServletRequest request);
}
