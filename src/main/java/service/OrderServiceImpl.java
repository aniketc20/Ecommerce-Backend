package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beans.OrderBean;
import repository.OrderRepository;
import models.OrderEntity;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
    private CartService cartService;
	
	@Override
	public void addOrder(OrderBean orderBean) {
		// TODO Auto-generated method stub
		OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderBean, orderEntity);
		orderRepository.save(orderEntity);
		cartService.clearCart("aniket");
	}
	
	@Override
	public List<OrderBean> findByUserName(String user) {
		// TODO Auto-generated method stub
		List<OrderEntity> orderEntity = orderRepository.findByUsername(user);
		List<OrderBean> orderBeans = new ArrayList<>();
        for (int i = 0; i < orderEntity.size(); i++) {
        	OrderBean orderBean = new OrderBean();
        	BeanUtils.copyProperties(orderEntity.get(i), orderBean);
            orderBeans.add(orderBean);
        }
		return orderBeans;
	}

}
