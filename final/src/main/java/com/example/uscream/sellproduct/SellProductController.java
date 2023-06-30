package com.example.uscream.sellproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/sellproducts")
public class SellProductController {
	
	@Autowired
	private SellProductService service;
	
	
	@PostMapping()
	public Map addSellProduct(SellProductDto dto) {
		SellProductDto d = service.save(dto);
		Map map = new HashMap();
		map.put("sellproduct", d);
		return map;
		
	}
	
	
	@GetMapping()
	public Map getAll() {
		ArrayList<SellProductDto> dlist = service.getAll();
		Map map = new HashMap();
		map.put("selllist", dlist);
		return map;
	}
	
	
	@GetMapping("/{sellproductnum}")
	public Map getById(@PathVariable("sellproductnum") int sellproductnum) {
		SellProductDto dto = service.getById(sellproductnum);
		Map map = new HashMap();
		map.put("sellproduct", dto);
		return map;
		
	}
	
	
	@DeleteMapping()
	public void delete(int sellproductnum) {
		service.delete(sellproductnum);
	}
	
	@PatchMapping()
	public Map edit(SellProductDto dto) {
		SellProductDto d = service.save(dto);
		Map map = new HashMap();
		map.put("sellproduct", dto);
		return map;
		
	}

}
