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


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/selling")
public class SellingController {
	@Autowired
	private SellingService service;
	
	// 판매내역 추가
	@PostMapping("/add") //
	public Map add(TempDto dto) {
		System.out.println(dto);
		String storeid = dto.getStoreid();
		int sellproductnum = dto.getSellproductnum();
		int sellingcnt = dto.getSellingcnt();
		int sellingprice = dto.getSellingprice();
		SellingDto list = service.save(storeid,sellproductnum,sellingcnt,sellingprice);
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
	@GetMapping("/dailysales")
	public ArrayList<Map<String, Object[]>> getByDailySales(){
		ArrayList<Map<String, Object[]>> list = service.getByDailySales();
		return list;
	}
		
	// 일별 전체 매출 (연,월 설정)
	@GetMapping("/dailysales/{year}/{month}") //
	public Map getByDailySales2(@PathVariable("year") int year, @PathVariable("month") int month) {
		ArrayList<Map<String, Object[]>> list = service.getByDailySales2(year, month);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	// 일별 전체 매출 (전체기간 스토어별)
	@GetMapping("/dailysales/{storeid}")
	public Map getByStoreAllDaySales(@PathVariable("storeid") String storeid) {
		ArrayList<Map<String, Object[]>> list = service.getByStoreAllDaySales(storeid);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	// 일별 전체 매출 (연,월,스토어별)
	@GetMapping("/dailysales/{storeid}/{year}/{month}") //
	public Map getByStoreDailySales(@PathVariable("storeid") String storeid, @PathVariable("year") int year, @PathVariable("month") int month) {
		ArrayList<Map<String, Object[]>> list = service.getByStoreDailySales(storeid, year, month);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	
	// 월별 전체 매출
	@GetMapping("/monthlysales") 
	public Map getByMonthSales() {
		ArrayList<Map<String, Object[]>> list = service.getByMonthSales();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	// 월별 전체 매출 (전체기간 스토어별)
	@GetMapping("/monthlysales/{keyword}") 
	public Map getByStoreAllMonthlySales(@PathVariable("keyword") String keyword) {
	    ArrayList<Map<String, Object[]>> list = service.getByStoreAllMonthlySales(keyword);
	    Map map = new HashMap();
	    map.put("list", list);
	    return map;
	}

	
	// 월별 전체 매출 (연도 설정)
	@GetMapping("/monthlysales2/{year}")
	public Map getByMonthlySales(@PathVariable("year") int year) {
		ArrayList<Map<String, Object[]>> list = service.getByMonthlySales(year);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	// 월별 전체 매출 (연도, 스토어별)
	@GetMapping("/monthlysales/{storeid}/{year}") 
	public Map getByStoreMonthlySales(@PathVariable("storeid") String storeid, @PathVariable("year") int year) {
		ArrayList<Map<String, Object[]>> list = service.getByStoreMonthlySales(storeid, year);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	// 월별 전체 매출 (연, 월, 스토어별)
	@GetMapping("/monthlysales/{storeid}/{year}/{month}")
	public Map getByYearAndMonthSales(@PathVariable("storeid") String storeid, @PathVariable("year") int year, @PathVariable("month") int month) {
		ArrayList<Map<String, Object[]>> list = service.getByYearAndMonthSales(storeid, year, month);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	

	// 연도 전체 매출 (선택한 연도 사이의 모든 매출 조회)
	@GetMapping("/yearlysales/{year1}/{year2}") //
	public Map getByYearlySales(@PathVariable("year1") int year1, @PathVariable("year2") int year2) {
		ArrayList<Map<String, Object[]>> list = service.getByYearlySales(year1, year2);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	} 
	
	
	// 연도 전체 매출 (선택한 연도 사이의 특정 지점 모든 매출 조회)
	@GetMapping("/yearlysales/{storeid}/{year1}/{year2}") //
	public Map getByStoreYearlySales(@PathVariable("storeid") String storeid, @PathVariable("year1") int year1, @PathVariable("year2") int year2) {
		ArrayList<Map<String, Object[]>> list = service.getByStoreYearlySales(storeid, year1, year2);
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}


	// 전체 기간 일별 랭킹 전체
	@GetMapping("/dailyrank") // * year 나오게 추가
	public Map getByDailyRank() {
		ArrayList<Map<String, Object[]>> list = service.getByDailyRank();
		Map map = new HashMap(); 
		map.put("list", list);
		return map;
	}
	
	
	// 전체 기간 월간 랭킹 전체
	@GetMapping("/monthlyrank") //
	public Map getByMonthlyRank() {
		ArrayList<Map<String, Object[]>> list = service.getByMonthlyRank();
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	
	// 일간 매출 TOP3 지점
	@GetMapping("/dailyrank3") // * year 나오게 추가
	public Map getByDailyRank3() {
		ArrayList<Map<String, Object[]>> list = service.getByDailyRank3();
		Map map = new HashMap(); 
		map.put("list", list);
		return map;
	}
	
	
	// 월간 매출 TOP3 지점
	@GetMapping("/monthlyrank3") // 
	public Map getByMonthlyRank3() {
		ArrayList<Map<String, Object[]>> list = service.getByMonthlyRank3();
		Map map = new HashMap();
		map.put("list", list);
		return map; 
	}
	
	// 월간 매출 TOP5 지점
	@GetMapping("/monthlyrank5/{year}/{month}") // 
	public Map getByMonthlyRank5(@PathVariable("year") int year, @PathVariable("month") int month) {
		ArrayList<Map<String, Object[]>> list = service.getByMonthlyRank5(year, month);
		Map map = new HashMap();
		map.put("list", list);
		return map; 
	}

}

