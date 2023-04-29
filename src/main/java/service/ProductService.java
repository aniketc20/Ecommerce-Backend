package service;

import org.json.JSONException;
import org.json.JSONObject;

import beans.ProductBean;
import models.ProductEntity;

public interface ProductService {
	
	void addProd(ProductBean prodBean);
	
	JSONObject getAll() throws JSONException;
	
	ProductEntity getProduct(int id);
}
