package com.example.uscream.netsales;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uscream.porder.PorderService;
import com.example.uscream.selling.SellingService;
import com.example.uscream.store.Store;
import com.example.uscream.store.StoreDto;
import com.example.uscream.store.StoreService;

@RestController
@RequestMapping("/netsales")
@CrossOrigin(origins = "*")
@EnableScheduling
public class NetsalesController {

	@Autowired
	NetsalesService NetsalesService;
	
	@Autowired
	SellingService SellingService;
	
	@Autowired
	PorderService PorderService;
	
	@Autowired
	StoreService StoreService;
	
	@Scheduled(cron = "0 46 17 23 * ?")	// 매월 첫째 날 자정에 실행
	public Map addNetsales() {
		
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		
		if (month == 1) {	// 2023년 1월이면 2022년 12월 조회
			month = 12;
			year -= 1;
		} else {
			month -= 1;
		}
		
		Map map = new HashMap();
		
		ArrayList<StoreDto> storeList = StoreService.getAll();
		NetsalesDto dto = new NetsalesDto();
		

		for (StoreDto vo : storeList) {
			int msellingprice = 0;
		    dto.setNetsalesdate(LocalDate.of(year, month, 1));
		    dto.setStoreid(new Store(vo.getStoreid(), vo.getStorename(), vo.getPwd(), vo.getManagername(), 
		            vo.getAccounttype(), vo.getStoreimg(), vo.getX(), vo.getY()));

		    ArrayList<Object[]> salesList = SellingService.getByYearAndMonthSales(vo.getStoreid(), year, month);
		    System.out.println("salesList : "+salesList);
		    if (!salesList.isEmpty()) {
		    	
		    	
		        Object[] salesData = salesList.get(0);
		        if (salesData[4] instanceof Number) {
		            msellingprice = ((Number) salesData[4]).intValue();
		        }
		    }

		    dto.setMsellingprice(msellingprice);
			dto.setMpsalary(0);
			dto.setMordercost(0);
			System.out.println(dto);
			NetsalesService.save(dto);
		}
	
		return map;
	}
	
}







