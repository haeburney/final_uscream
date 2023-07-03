package com.example.uscream.selling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uscream.store.Store;
import com.example.uscream.store.StoreDto;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/selling")
public class SellingController {
	@Autowired
	private SellingService service;
	
	// 판매내역 추가
	@PostMapping("/add") //
	public Map add(SellingDto dto) {
		SellingDto list = service.save(dto);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	// 전체 판매내역 검색
	@GetMapping("")	//
	public Map getAll() {
		ArrayList<SellingDto> list = service.getAll();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
		
	// 일별 전체 매출
	@GetMapping("/dailysales/{year}/{month}") //
	public Map getByDaySales(@PathVariable("year") int year, @PathVariable("month") int month) {
		ArrayList<Map<String, Object[]>> list = service.getByDailySales(year, month);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	// 특정지점 특정월의 일 매출
	@GetMapping("/dailysales/{storeid}/{year}/{month}") //
	public Map getByStoreDaySales(@PathVariable("storeid") String storeid, @PathVariable("year") int year, @PathVariable("month") int month) {
		ArrayList<Map<String, Object[]>> list = service.getByStoreDailySales(storeid, year, month);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	// 특정지점의 전체 기간 일 매출
	@GetMapping("dailysales/{storeid}")
	public Map getByStoreAllDaySales(@PathVariable("storeid") String storeid) {
		ArrayList<Map<String, Object[]>> list = service.getByStoreAllDaySales(storeid);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	// 1년 단위 전체 월별 매출
	@GetMapping("/monthlysales/{year}") // * year 나오게 추가
	public Map getByMonthSales(@PathVariable("year") int year) {
		ArrayList<Map<String, Object[]>> list = service.getByMonthlySales(year);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	// 1년 단위 특정지점 월별 매출
	@GetMapping("/monthlysales/{storeid}/{year}") // * year 나오게 추가 
	public Map getByStoreMonthSales(@PathVariable("storeid") String storeid, @PathVariable("year") int year) {
		ArrayList<Map<String, Object[]>> list = service.getByStoreMonthlySales(storeid, year);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	// 일간 매출 TOP3 지점
	@GetMapping("/dailyrank3") // * year 나오게 추가
	public Map getByDayRank3() {
		ArrayList<Map<String, Object[]>> list = service.getByDailyRank3();
		Map map = new HashMap(); 
		map.put("list", list);
		return map;
	}
	
	// 월간 매출 TOP3 지점
	@GetMapping("/monthlyrank3") // 
	public Map getByMonthRank3() {
		ArrayList<Map<String, Object[]>> list = service.getByMonthlyRank3();
		Map map = new HashMap();
		map.put("list", list);
		return map; 
	}

	// 일간 전체 매출
	@GetMapping("/dailyrank") // * year 나오게 추가
	public Map getByDayRank() {
		ArrayList<Map<String, Object[]>> list = service.getByDailyRank();
		Map map = new HashMap(); 
		map.put("list", list);
		return map;
	}
	
	// 월간 전체 매출
	@GetMapping("/monthlyrank") //
	public Map getByMonthRank() {
		ArrayList<Map<String, Object[]>> list = service.getByMonthlyRank();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	// 설정 기간 동안의 전체지점 연매출 조회
	@GetMapping("/yearlysales/{year1}/{year2}") //
	public Map getByYearlySales(@PathVariable("year1") int year1, @PathVariable("year2") int year2) {
		ArrayList<Map<String, Object[]>> list = service.getByYearlySales(year1, year2);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	} 
	
	// 설정 기간 동안의 특정지점 연매출 조회
	@GetMapping("/yearlysales/{storeid}/{year1}/{year2}") //
	public Map getByStoreYearlySales(@PathVariable("storeid") String storeid, @PathVariable("year1") int year1, @PathVariable("year2") int year2) {
		ArrayList<Map<String, Object[]>> list = service.getByStoreYearlySales(storeid, year1, year2);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	
	@GetMapping("/monthlysales/{storeid}/{year}/{month}") //ㄴ
	public Map getByYearAndMonthSales(@PathVariable("storeid") String storeid, @PathVariable("year") int year, @PathVariable("month") int month) {
		ArrayList<Map<String, Object[]>> list = service.getByYearAndMonthSales(storeid, year, month);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	@GetMapping("/dailysales")
	public ArrayList<Map<String, Object[]>> getByDailySales(){
		ArrayList<Map<String, Object[]>> list = service.getByDailySales();
		return list;
	}
}

