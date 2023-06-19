package com.example.uscream.order;

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
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@PostMapping()
	public Map order(OrderDto dto,@PathVariable("num") int num) {
		OrderDto d = service.save(dto);
		Map map = new HashMap();
		map.put("order",d);
		
		return map;
	}
	
	@GetMapping()
	public Map getAll() {
		ArrayList<OrderDto> dlist = service.getAll();
		Map map = new HashMap();
		map.put("orderlist", dlist);
		return map;
		
	}
	
	@GetMapping("/detail/{ordernum}")
	public Map getByOrdernum(@PathVariable("ordernum") String ordernum) {
		ArrayList<OrderDto> dlist = service.getByOrderNum(ordernum);
		Map map = new HashMap();
		map.put("Storeoder",dlist);
		return map;
		
	}
	
	@PatchMapping()
	public Map editOrder(int tempnum ,OrderDto dto) {
		OrderDto d = service.getById(tempnum);
		dto.setTempnum(d.getTempnum());
		dto.setStoreid(d.getStoreid());
		dto.setProduct(d.getProduct());
		dto.setOrderdate(d.getOrderdate());
		dto.setConfirmdate(d.getConfirmdate());
		dto.setConfirm(d.isConfirm());
		OrderDto d2 = service.save(dto);
		Map map = new HashMap();
		map.put("order", d2);
		return map;
	}
	
	@PatchMapping("/confirm/{num}")
	public void confirm(@PathVariable("num") int tempnum) {
		service.confirm(tempnum);
	}

}
