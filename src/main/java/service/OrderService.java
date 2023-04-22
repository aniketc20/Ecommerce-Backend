package service;

import java.util.List;

import beans.*;
public interface OrderService {
	void addOrder(OrderBean orderBean);
	List<OrderBean> findByUserName(String user);
}
