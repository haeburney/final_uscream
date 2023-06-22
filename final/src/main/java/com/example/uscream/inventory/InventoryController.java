package com.example.uscream.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/inventorys")
public class InventoryController {
	
	@Autowired
	private InventoryService service;
	
	@GetMapping()
	public Map getAll() {
		ArrayList<InventoryDto> dlist = service.getAll();
		Map map = new HashMap();
		map.put("inventorylist", dlist);
		return map;
	}
	
	@PostMapping()
	public Map add(InventoryDto dto) {
		InventoryDto d = service.save(dto);
		Map map = new HashMap();
		map.put("invnetory",d);
		return map;
		
	}
	
	@PatchMapping()
	public Map editAmount(InventoryDto dto, int inventorynum) {
		InventoryDto temp = service.getById(inventorynum);
		dto.setInventorynum(inventorynum);
		dto.setProductname(temp.getProductname());
		dto.setStoreid(temp.getStoreid());
		InventoryDto d = service.save(dto);
		Map map = new HashMap();
		map.put("invnetory",d);
		return map;
		
	}
	
	@GetMapping("/{storeid}")
	public Map getByStoreid(@PathVariable("storeid") String storeid) {
		ArrayList<InventoryDto> dlist = service.getByStoreid(storeid);
		Map map = new HashMap();
		map.put("inventorylist", dlist);
		return map;
	}
	
	
	

}
