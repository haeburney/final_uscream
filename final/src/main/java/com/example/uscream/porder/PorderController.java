package com.example.uscream.porder;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.example.uscream.store.Store;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class PorderController {

	@Autowired
	private PorderService service;

	@PostMapping()
	public Map order(@RequestBody PorderDto[] dto) {
		int ordercnt = service.save(dto);
		Map map = new HashMap();
		map.put("ordercnt", ordercnt);

		return map;
	}

	@GetMapping()
	public Map getAll() {
		ArrayList<PorderDto> dlist = service.getAll();
		Map map = new HashMap();
		map.put("orderlist", dlist);
		return map;

	}
	@GetMapping("/notconfirm")
	public Map getNotConfirm() {
		ArrayList<Map<String, String>> dlist = service.getNotConfirm();
		Map map = new HashMap();
		map.put("orderlist", dlist);
		return map;
		
	}
	@GetMapping("/notconfirm/{storeid}")
	public Map getNotConfirm(@PathVariable("storeid") String storeid) {
		ArrayList<Map<String, String>> dlist = service.getNotConfirmAndStoreid(storeid);
		Map map = new HashMap();
		map.put("orderlist", dlist);
		return map;
		
	}

	@GetMapping("/detail/{storeid}/{orderdate}")
	public Map getByOrdernum(@PathVariable("storeid") String storeid, @PathVariable("orderdate") String orderdate) {
		String ordernum = orderdate + "#" + storeid;
		ArrayList<PorderDto> dlist = service.getByOrderNum(ordernum);
		Map map = new HashMap();
		map.put("Storeorder", dlist);
		return map;

	}

	@PatchMapping()
	public Map editOrder(int tempnum, PorderDto dto) {
		PorderDto d = service.getById(tempnum);
		dto.setTempnum(d.getTempnum());
		dto.setStoreid(d.getStoreid());
		dto.setProduct(d.getProduct());
		dto.setOrderdate(d.getOrderdate());
		dto.setConfirmdate(d.getConfirmdate());
		dto.setConfirm(d.getConfirm());
		PorderDto d2 = service.edit(dto);
		Map map = new HashMap();
		map.put("order", d2);
		return map;
	}

//	@GetMapping("/detail/{ordernum}/{storeid}")
//	public Map getDetail(@PathVariable("ordernum") String ordernum,@PathVariable("storeid") String storeid) {
//		ArrayList<PorderDto> dlist = service.getStoreOrder(ordernum, storeid);
//		Map map = new HashMap();
//		map.put("detail", dlist);
//		return map;
//				
//	
//	}

	@PatchMapping("/confirm/{tempnum}/{num}")
	public void confirm(@PathVariable("tempnum") int tempnum,@PathVariable("num") int num) {
		service.confirm(tempnum,num);
	}

	@GetMapping("/orderlist")
	public Map getOrderLsit() {
		ArrayList<Map<String, String>> orderlist = service.getOrdernum();
		Map<String, Object> map = new HashMap();
		map.put("orderlist", orderlist);
		return map;

	}
	@PatchMapping("/edit/{tempnum}/{amount}/{ordercost}")
	public void confirm(@PathVariable("tempnum") int tempnum,@PathVariable("amount") int amount, @PathVariable("ordercost") int ordercost) {
		service.updateAmount(amount, ordercost, tempnum);
	}
	
	

	@GetMapping("/orderlist/{store}")
	public Map getStoreOrderLsit(@PathVariable("store") String store) {
		ArrayList<Map<String, String>> orderlist = service.getStoreOrderlist(store);
		Map<String, Object> map = new HashMap();
		map.put("orderlist", orderlist);
		return map;

	}

	@GetMapping("/ordercost/{storeid}/{year}/{month}")
	public Map getMonthlyOrderCost(@PathVariable("storeid") String storeid, @PathVariable("year") int year,
			@PathVariable("month") int month) {
		
		ArrayList<Map<String, String>> monthlyorder = service.getMonthlyOrderCost(storeid, year, month);
		Map<String, Object> map = new HashMap();
		map.put("mordrcost",monthlyorder);
		return map;

	}

}
