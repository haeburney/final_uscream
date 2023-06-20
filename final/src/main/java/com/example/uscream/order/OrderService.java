package com.example.uscream.order;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.store.Store;

@Service
public class OrderService {
	
	@Autowired
	private OrderDao dao;
	
	private ArrayList<OrderDto> change(ArrayList<Order> list){
		ArrayList<OrderDto> dlist = new ArrayList<OrderDto>();
		for(Order entity:list) {
			dlist.add(new OrderDto(entity.getTempnum(),entity.getOrdernum(),entity.getStoreid(),entity.getProduct(),entity.getAmount(),entity.getOrderdate(),entity.getConfirmdate(),entity.getOrdercost(),entity.isConfirm()));
		}
		return dlist;
	}
	
	public OrderDto save(OrderDto dto) {
		Order entity = dao.save(new Order(dto.getTempnum(),dto.getOrdernum(),dto.getStoreid(),dto.getProduct(),dto.getAmount(),dto.getOrderdate(),dto.getConfirmdate(),dto.getOrdercost(),dto.isConfirm()));
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
	
	public OrderDto getById(int tempnum) {
		Order entity = dao.findById(tempnum).orElse(null);
		OrderDto dto = new OrderDto(entity.getTempnum(),entity.getOrdernum(),entity.getStoreid(),entity.getProduct(),entity.getAmount(),entity.getOrderdate(),entity.getConfirmdate(),entity.getOrdercost(),entity.isConfirm());
		return dto;
		
	}
	
	public ArrayList<OrderDto> getAll(){
		ArrayList<Order> list = (ArrayList<Order>) dao.findAll();
		return change(list);
	}
	
	public ArrayList<OrderDto> getByOrderNum(String ordernum){
		ArrayList<Order> list = dao.findByOrdernum(ordernum);
		return change(list);
	}
	
	public ArrayList<OrderDto> getStoreOrder(String ordernum, String storeid){
		Store store = new Store(storeid,"","","",0,0,0);
		ArrayList<Order> list = dao.findByOrdernumAndStoreid(ordernum, store);
		return change(list);
		
	}
	
	public void confirm(int tempnum) {
		dao.confirm(tempnum);
	}
	
	public void delete(int tempnum) {
		dao.deleteById(tempnum);
	}

}
