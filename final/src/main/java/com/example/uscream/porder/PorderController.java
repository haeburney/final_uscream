package com.example.uscream.porder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class PorderController {
	
	@Autowired
	private PorderService service;
	
	@PostMapping()
	public Map order(@RequestBody PorderDto[] dto) {
		System.out.println(dto);
		int ordercnt= service.save(dto);
		Map map = new HashMap();
		map.put("ordercnt",ordercnt);
		
		return map;
	}
	
	@GetMapping()
	public Map getAll() {
		ArrayList<PorderDto> dlist = service.getAll();
		Map map = new HashMap();
		map.put("orderlist", dlist);
		System.out.println(dlist);
		return map;
		
	}
	
	@GetMapping("/detail/{ordernum}")
	public Map getByOrdernum(@PathVariable("ordernum") String ordernum) {
		ArrayList<PorderDto> dlist = service.getByOrderNum(ordernum);
		Map map = new HashMap();
		System.out.println(dlist);
		map.put("Storeoder",dlist);
		System.out.println(map);
		return map;
		
	}
	
	@PatchMapping()
	public Map editOrder(int tempnum ,PorderDto dto) {
		PorderDto d = service.getById(tempnum);
		dto.setTempnum(d.getTempnum());
		dto.setStoreid(d.getStoreid());
		dto.setProduct(d.getProduct());
		dto.setOrderdate(d.getOrderdate());
		dto.setConfirmdate(d.getConfirmdate());
		dto.setConfirm(d.isConfirm());
		PorderDto d2 = service.edit(dto);
		Map map = new HashMap();
		map.put("order", d2);
		return map;
	}
	
	@PatchMapping("/confirm/{num}")
	public void confirm(@PathVariable("num") int tempnum) {
		service.confirm(tempnum);
	}
	
	@GetMapping("/orderlist")
	public Map getOrderLsit() {
		ArrayList<Map<String, String>> orderlist = service.getOrdernum();
		System.out.println("orderlist:" +orderlist);
		Map<String, Object> map = new HashMap();
		map.put("orderlist", orderlist);
		System.out.println("map"+map);
		return map;
		
	}

}
