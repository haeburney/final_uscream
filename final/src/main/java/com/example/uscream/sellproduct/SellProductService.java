package com.example.uscream.sellproduct;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellProductService {

	@Autowired
	private SellProductDao dao;
	
	private ArrayList<SellProductDto> change(ArrayList<SellProduct> list){
		ArrayList<SellProductDto> dlist = new ArrayList<SellProductDto>();
		for(SellProduct entity : list) {
			dlist.add(new SellProductDto(entity.getSellproductnum(),entity.getSellproductname(),entity.getSellproductprice()));
		}
		return dlist;		
	}
	
	public SellProductDto save(SellProductDto dto) {
		SellProduct entity = dao.save(new SellProduct(dto.getSellproductnum(),dto.getSellproductname(),dto.getSellproductprice()));
		dto.setSellproductnum(entity.getSellproductnum());
		dto.setSellproductname(entity.getSellproductname());
		dto.setSellproductprice(entity.getSellproductprice());
		return dto;
		
	}
	
	public SellProductDto getById(int sellproductnum) {
		SellProduct entity = dao.findById(sellproductnum).orElse(null);
		SellProductDto dto = new SellProductDto(entity.getSellproductnum(),entity.getSellproductname(),entity.getSellproductprice());
		return dto;
	}
	
	public ArrayList<SellProductDto> getAll(){
		ArrayList<SellProduct> list = (ArrayList<SellProduct>) dao.findAll();
		return change(list);
	}
	
	public void delete(int sellproductnum) {
		dao.deleteById(sellproductnum);
	}
}
