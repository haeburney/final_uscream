package com.example.uscream.porder;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.store.Store;

@Service
public class PorderService {
	
	@Autowired
	private PorderDao dao;
	
	private ArrayList<PorderDto> change(ArrayList<Porder> list){
		ArrayList<PorderDto> dlist = new ArrayList<PorderDto>();
		for(Porder entity:list) {
			dlist.add(new PorderDto(entity.getTempnum(),entity.getOrdernum(),entity.getStoreid(),entity.getProduct(),entity.getAmount(),entity.getOrderdate(),entity.getConfirmdate(),entity.getOrdercost(),entity.isConfirm()));
		}
		return dlist;
	}
	
	public PorderDto save(PorderDto dto) {
		Porder entity = dao.save(new Porder(dto.getTempnum(),dto.getOrdernum(),dto.getStoreid(),dto.getProduct(),dto.getAmount(),dto.getOrderdate(),dto.getConfirmdate(),dto.getOrdercost(),dto.isConfirm()));
		dto.setTempnum(entity.getTempnum());
		dto.setOrdernum(entity.getOrdernum());
		dto.setStoreid(entity.getStoreid());
		dto.setProduct(entity.getProduct());
		dto.setAmount(entity.getAmount());
		dto.setOrderdate(entity.getOrderdate());
		dto.setConfirmdate(entity.getConfirmdate());
		dto.setConfirm(entity.isConfirm());
		return dto;
	
	}
	
	public PorderDto getById(int tempnum) {
		Porder entity = dao.findById(tempnum).orElse(null);
		PorderDto dto = new PorderDto(entity.getTempnum(),entity.getOrdernum(),entity.getStoreid(),entity.getProduct(),entity.getAmount(),entity.getOrderdate(),entity.getConfirmdate(),entity.getOrdercost(),entity.isConfirm());
		return dto;
		
	}
	
	public ArrayList<PorderDto> getAll(){
		ArrayList<Porder> list = (ArrayList<Porder>) dao.findAll();
		return change(list);
	}
	
	public ArrayList<PorderDto> getByOrderNum(String ordernum){
		ArrayList<Porder> list = dao.findByOrdernum(ordernum);
		return change(list);
	}
	
	public ArrayList<PorderDto> getStoreOrder(String ordernum, String storeid){
		Store store = new Store(storeid,"","","",0,"", 0,0);
		ArrayList<Porder> list = dao.findByOrdernumAndStoreid(ordernum, store);
		return change(list);
		
	}
	
	public void confirm(int tempnum) {
		dao.confirm(tempnum);
	}
	
	public void delete(int tempnum) {
		dao.deleteById(tempnum);
	}

}
