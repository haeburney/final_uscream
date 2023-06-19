package com.example.uscream.selling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.store.Store;

@Service
public class SellingService {

	@Autowired
	private SellingDao dao;
	
	private ArrayList<SellingDto> change(ArrayList<Selling> list){
		ArrayList<SellingDto> dlist = new ArrayList<>();
		for(Selling entity :list) {
			dlist.add(new SellingDto(entity.getSellingnum(),entity.getSellproduct(),entity.getStoreid(),entity.getSellingdate(),entity.getSellingcnt(),entity.getSellingprice()));
			
		}
		return dlist;
	}
	
	public SellingDto save(SellingDto dto) {
		Selling entity = dao.save(new Selling(dto.getSellingnum(),dto.getSellproduct(),dto.getStoreid(),dto.getSellingdate(),dto.getSellingcnt(),dto.getSellingprice()));
		dto.setSellingnum(entity.getSellingnum());
		dto.setSellproduct(entity.getSellproduct());
		dto.setStoreid(entity.getStoreid());
		dto.setSellingdate(entity.getSellingdate());
		dto.setSellingcnt(entity.getSellingcnt());
		dto.setSellingprice(entity.getSellingprice());
		return dto;
	}
	
	public SellingDto getById(int sellingnum) {
		Selling entity =dao.findById(sellingnum).orElse(null);
		SellingDto dto = new SellingDto(entity.getSellingnum(),entity.getSellproduct(),entity.getStoreid(),entity.getSellingdate(),entity.getSellingcnt(),entity.getSellingprice());
		return dto;
	}
	
	public ArrayList<SellingDto> getAll(){
		ArrayList<Selling> list = (ArrayList<Selling>) dao.findAll();
		return change(list);
		
	}
	
	public ArrayList<SellingDto> getByStoreid(String storeid){
		Store store = new Store(storeid, "", 0, 0);
		ArrayList<Selling> list = dao.findByStoreid(store);
		return change(list);
		
	}
	
	public ArrayList<SellingDto> getByDate(Date sellingdate){
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//		String date = dateFormat.format(sellingdate);
		ArrayList<Selling> list = dao.findBySellingDate(sellingdate);
		return change(list);
		
		
	}
}
