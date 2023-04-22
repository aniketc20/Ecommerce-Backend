package service;

import java.util.List;

import beans.ProductBean;
import models.ProductEntity;

public interface ProductService {
	
	void addProd(ProductBean prodBean);
	
	List<ProductBean> getAll();
	
	ProductEntity getProduct(int id);
}
