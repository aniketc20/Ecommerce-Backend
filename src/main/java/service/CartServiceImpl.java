package service;

import repository.CartRepository;
import repository.ProductRepository;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import beans.CartBean;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import models.CartEntity;
import models.ProductEntity;
import models.ProductOrder;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Value("${jwt.secret}")
    private String SECRET_KEY;

	@Override
	public CartBean addToCart(Integer productId, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = getUsernameFromToken(request);
		CartEntity cartEntity = cartRepository.findByUsername(userName);
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
						product.getPrice(), product.getDesc(), 1, product.getImgUrl());
				cartProducts.add(productOrder);
				cartEntity.setProducts(cartProducts);
			}
			cartRepository.save(cartEntity);
		}
		else {
			Set<ProductOrder> productOrders = new HashSet<ProductOrder>();
			ProductEntity product = productRepository.getById(productId);
			ProductOrder productOrder = new ProductOrder(productId, product.getProductName(), 
					product.getPrice(), product.getDesc(), 1, product.getImgUrl());
			productOrders.add(productOrder);
			cartEntity = new CartEntity();
			cartEntity.setProducts(productOrders);
			cartEntity.setUsername(userName);
			cartRepository.save(cartEntity);
		}
		BeanUtils.copyProperties(cartEntity, cartBean);
		return cartBean;
	}
	
	@Override
	public CartBean decreaseQty(Integer productId, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = getUsernameFromToken(request);
		CartEntity cartEntity = cartRepository.findByUsername(userName);
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
	public CartBean getCart(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = getUsernameFromToken(request);
		CartEntity cartEntity = cartRepository.findByUsername(userName);
		CartBean cartBean = new CartBean();
		if(cartEntity!=null) {
			BeanUtils.copyProperties(cartEntity, cartBean);
		}
		return cartBean;
	}

	@Override
	public void clearCart(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = getUsernameFromToken(request);
		CartEntity cartEntity = cartRepository.findByUsername(userName);
		Set<ProductOrder> cartProducts = cartEntity.getProducts();
		cartProducts.clear();
		cartRepository.save(cartEntity);
	}

	@Override
	public CartBean removeItemFromCart(Integer productId, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = getUsernameFromToken(request);
		CartEntity cartEntity = cartRepository.findByUsername(userName);
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
