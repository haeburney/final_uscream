package com.example.uscream.inventory;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.store.Store;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryDao dao;
	
	private ArrayList<InventoryDto> change(ArrayList<Inventory> list){
		ArrayList<InventoryDto> dlist = new ArrayList<InventoryDto>();
		for(Inventory entity : list) {
			dlist.add(new InventoryDto(entity.getInventorynum(),entity.getStoreid(),entity.getProductname(),entity.getAmount()));
			
		}
		return dlist;
	}
	
	public InventoryDto save(InventoryDto dto) {
		Inventory entity = dao.save(new Inventory(dto.getInventorynum(),dto.getStoreid(),dto.getProductname(),dto.getAmount()));
		dto.setInventorynum(entity.getInventorynum());
		dto.setStoreid(entity.getStoreid());
		dto.setProductname(entity.getProductname());
		dto.setAmount(entity.getAmount());
		return dto;
		
	}
	
	public ArrayList<InventoryDto> getAll(){
		ArrayList<Inventory> list = (ArrayList<Inventory>) dao.findAll();
		return change(list);
	}
	
	public InventoryDto getById(int inventorynum) {
		Inventory entity = dao.findById(inventorynum).orElse(null);
		InventoryDto dto =new InventoryDto(entity.getInventorynum(),entity.getStoreid(),entity.getProductname(),entity.getAmount());
		return dto;
		
	}
	
	public ArrayList<InventoryDto> getByStoreid(String stroeid){
		Store store = new Store(stroeid, "", "", "", 0, "", 0, 0);
		ArrayList<Inventory> list = dao.findByStoreid(store);
		return change(list);
	}
}
