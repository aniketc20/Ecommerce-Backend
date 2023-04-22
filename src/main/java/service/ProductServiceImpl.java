package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.ProductEntity;
import beans.ProductBean;
import repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public void addProd(ProductBean prodBean) {
		// TODO Auto-generated method stub
		ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(prodBean, productEntity);
        productRepository.save(productEntity);
	}

	@Override
	public List<ProductBean> getAll() {
		// TODO Auto-generated method stub
		List<ProductEntity> products = productRepository.findAll();
        List<ProductBean> productBeans = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
        	ProductBean productBean = new ProductBean();
            BeanUtils.copyProperties(products.get(i), productBean);
            productBeans.add(productBean);
        }
        return productBeans;
	}

	@Override
	public ProductEntity getProduct(int id) {
		// TODO Auto-generated method stub
		return productRepository.getById(id);
	}

}
