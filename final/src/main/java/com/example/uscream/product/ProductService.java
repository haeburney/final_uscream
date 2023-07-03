package com.example.uscream.product;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao dao;
	
	private ArrayList<ProductDto> change(ArrayList<Product> list){
		ArrayList<ProductDto> dlist = new ArrayList<ProductDto>();
		for(Product entity:list) {
			dlist.add(new ProductDto(entity.getProductnum(),entity.getProductname(),entity.getProductimg(),entity.getProductinfo(),entity.getProductcategory(),entity.getCost(),entity.isOrderble(),null));
		}
		return dlist;
		
	}
	
	
	public ProductDto save(ProductDto dto) {
		Product entity = dao.save(new Product(dto.getProductnum(),dto.getProductname(),dto.getProductimg(),dto.getProductinfo(),dto.getProductcategory(),dto.getCost(),dto.isOrderble()));
		dto.setProductnum(entity.getProductnum());
		dto.setProductname(entity.getProductname());
		dto.setProductimg(entity.getProductimg());
		dto.setProductinfo(entity.getProductinfo());
		dto.setCost(entity.getCost());
		dto.setOrderble(entity.isOrderble());
		return dto;
		
	}
	
	public ProductDto getById(int num) {
		Product entity = dao.findById(num).orElse(null);
		ProductDto dto = new ProductDto(entity.getProductnum(),entity.getProductname(),entity.getProductimg(),entity.getProductinfo(),entity.getProductcategory(),entity.getCost(),entity.isOrderble(),null);
		return dto;
		
	}
	
	public ArrayList<ProductDto> getAll(){
		ArrayList<Product> list = (ArrayList<Product>) dao.findAll();
		return change(list);
		
	}
	
	public ArrayList<ProductDto> getByProductName(String productname){
		ArrayList<Product> list = (ArrayList<Product>)dao.findByProductname(productname);
		return change(list);
	}
	
	public void deleteProduct(int num) {
		dao.deleteById(num);
	}
	
	public ArrayList<ProductDto> getByProductcategory(String productcategory){
		ArrayList<Product> list = (ArrayList<Product>)dao.findByProductcategory(productcategory);
		
		return change(list);
	}
	
}
