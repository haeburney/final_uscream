package com.example.uscream.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao dao;
	
	public ProductDto save(ProductDto dto) {
		Product entity = dao.save(new Product(dto.getProductnum(),dto.getProductname(),dto.getProductimg(),dto.getProductinfo(),dto.getCost(),dto.isOrderble()));
		dto.setProductnum(entity.getProductnum());
		dto.setProductname(entity.getProductname());
		dto.setProductimg(entity.getProductimg());
		dto.setProductinfo(entity.getProductinfo());
		dto.setCost(entity.getCost());
		dto.setOrderble(entity.isOrderble());
		return dto;
		
		
		
		
	}
}
