package service;

import repository.CartRepository;
import repository.ProductRepository;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.CartBean;
import models.CartEntity;
import models.ProductEntity;
import models.ProductOrder;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public CartBean addToCart(Integer productId) {
		// TODO Auto-generated method stub
		CartEntity cartEntity = cartRepository.findByUsername("aniket");
		CartBean cartBean = new CartBean();
		if(cartEntity != null) {
			Set<ProductOrder> cartProducts = cartEntity.getProducts();
			boolean prodExists = false;
			for (ProductOrder productOrder : cartProducts) {
			    if(productOrder.getProductId().equals(productId)) {
			    	productOrder.setQty(productOrder.getQty() + 1);
			    	prodExists = true;
			    	break;
			    }
			}
			if(!prodExists) {
				ProductEntity product = productRepository.getById(productId);
				ProductOrder productOrder = new ProductOrder(productId, product.getProductName(), 
						product.getPrice(), product.getDesc(), 1);
				cartProducts.add(productOrder);
				cartEntity.setProducts(cartProducts);
			}
			cartRepository.save(cartEntity);
		}
		else {
			Set<ProductOrder> productOrders = new HashSet<ProductOrder>();
			ProductEntity product = productRepository.getById(productId);
			ProductOrder productOrder = new ProductOrder(productId, product.getProductName(), 
					product.getPrice(), product.getDesc(), 1);
			productOrders.add(productOrder);
			cartEntity = new CartEntity();
			cartEntity.setProducts(productOrders);
			cartEntity.setUsername("aniket");
			cartRepository.save(cartEntity);
		}
		BeanUtils.copyProperties(cartEntity, cartBean);
		return cartBean;
	}
	
	@Override
	public CartBean decreaseQty(Integer productId) {
		// TODO Auto-generated method stub
		CartEntity cartEntity = cartRepository.findByUsername("aniket");
		CartBean cartBean = new CartBean();
		Set<ProductOrder> cartProducts = cartEntity.getProducts();
		for (ProductOrder productOrder : cartProducts) {
		    if(productOrder.getProductId().equals(productId)) {
		    	productOrder.setQty(productOrder.getQty() - 1);
		    	break;
		    }
		}
		cartRepository.save(cartEntity);
		BeanUtils.copyProperties(cartEntity, cartBean);
		return cartBean;
	}

	@Override
	public CartBean getCart(String username) {
		// TODO Auto-generated method stub
		CartEntity cartEntity = cartRepository.findByUsername(username);
		CartBean cartBean = new CartBean();
		if(cartEntity!=null) {
			BeanUtils.copyProperties(cartEntity, cartBean);
		}
		return cartBean;
	}

	@Override
	public void clearCart(String username) {
		// TODO Auto-generated method stub
		CartEntity cartEntity = cartRepository.findByUsername(username);
		Set<ProductOrder> cartProducts = cartEntity.getProducts();
		cartProducts.clear();
		cartRepository.save(cartEntity);
	}

	@Override
	public CartBean removeItemFromCart(Integer productId) {
		// TODO Auto-generated method stub
		CartEntity cartEntity = cartRepository.findByUsername("aniket");
		Set<ProductOrder> cartProducts = cartEntity.getProducts();
		for (ProductOrder productOrder : cartProducts) {
		    if(productOrder.getProductId().equals(productId)) {
		    	cartProducts.remove(productOrder);
		    	break;
		    }
		}
		cartRepository.save(cartEntity);
		CartBean cartBean = new CartBean();
		BeanUtils.copyProperties(cartEntity, cartBean);
		return cartBean;
	}
}
