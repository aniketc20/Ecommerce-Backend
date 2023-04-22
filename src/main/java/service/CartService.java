package service;

import beans.CartBean;

public interface CartService {
	public CartBean addToCart(Integer productId);
	public CartBean getCart(String username);
	public void clearCart(String username);
	public CartBean removeItemFromCart(Integer productId);
	public CartBean decreaseQty(Integer productId);
}
