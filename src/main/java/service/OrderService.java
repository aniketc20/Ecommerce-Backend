package service;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import beans.*;
import jakarta.servlet.http.HttpServletRequest;

public interface OrderService {
	void addOrder(HttpServletRequest request) throws JSONException, IOException;
	List<OrderBean> userOrders(HttpServletRequest request);
}
