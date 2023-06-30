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
	
	@PatchMapping("{storeid}/{inventorynum}/{amount}")
	public void editAmount(@PathVariable("storeid") String storeid, @PathVariable("inventorynum") int inventorynum, @PathVariable("amount") int amount) {
		service.updateAmount(amount, inventorynum,storeid);
	}
	
	@GetMapping("/{storeid}")
	public Map getByStoreid(@PathVariable("storeid") String storeid) {
		ArrayList<InventoryDto> dlist = service.getByStoreid(storeid);
		Map map = new HashMap();
		map.put("inventorylist", dlist);
		return map;
	}
	
	
	

}
