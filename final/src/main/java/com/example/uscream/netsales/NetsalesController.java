package com.example.uscream.netsales;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uscream.porder.PorderService;
import com.example.uscream.selling.SellingService;

@RestController
public class NetsalesController {

	@Autowired
	NetsalesService nsService;
	
	@Autowired
	SellingService spService;
	
	@Autowired
	PorderService oService;
	
	@PostMapping("")
	public Map addNetsales(NetsalesDto dto) {
		Map map = new HashMap();
		
		//dto.setSellingprice(0);
		
		
		return map;
	}
}
