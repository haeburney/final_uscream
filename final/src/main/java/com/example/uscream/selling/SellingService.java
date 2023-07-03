package com.example.uscream.selling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.uscream.store.Store;

@Service
public class SellingService {
	@Autowired
	private SellingDao dao;
 
	
	// 판매내역 저장  
	public SellingDto save(SellingDto dto) {
		dao.save(new Selling(dto.getSellingnum(), dto.getSellproduct(), dto.getStoreid(), 
				dto.getSellingdate(), dto.getSellingcnt(), dto.getSellingprice()));
		return dto;
	}
	
	// 판매내역 전체 검색 
	public ArrayList<SellingDto> getAll() {
		ArrayList<Selling> list = (ArrayList<Selling>) dao.findAll();
		ArrayList<SellingDto> list2 = new ArrayList<SellingDto>();
		for(Selling vo : list) {
			list2.add((new SellingDto(vo.getSellingnum(), vo.getSellproduct(), vo.getStoreid(), 
					vo.getSellingdate(), vo.getSellingcnt(), vo.getSellingprice())));
		}
		return list2;
	}	
	
	
	// 일별 전체 매출
	public ArrayList<Map<String, Object[]>> getByDailySales(int year, int month){
		ArrayList<Map<String, Object[]>> list = dao.findTotalpriceByDay(year, month);
		System.out.println(list);
		return list;
	}
	
	// 특정지점의 전체 기간 일 매출
	public ArrayList<Map<String, Object[]>> getByStoreAllDaySales(String storeid) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByStoreAndAllDay(storeid);
		return list;
	}
			

	// 특정지점 특정월의 일 매출
	public ArrayList<Map<String, Object[]>> getByStoreDailySales(String storeid, int year, int month) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByStoreAndDay(storeid, year, month);
		return list;
	}
		
	// 1년 단위 전체 월별 매출
	public ArrayList<Map<String, Object[]>> getByMonthlySales(int year) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByMonth(year);
		return list;
	}
	
	// 1년 단위 특정지점 월별 매출
	public ArrayList<Map<String, Object[]>> getByStoreMonthlySales(String storeid, int year) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByStoreAndMonth(storeid, year);
		return list;	
	}
	
	// 일간 매출 TOP3 지점
	public ArrayList<Map<String, Object[]>> getByDailyRank3() {
		ArrayList<Map<String, Object[]>> list = dao.findByDayRank3Store();
		return list;
	}
	
	// 월간 매출 TOP3 지점
	public ArrayList<Map<String, Object[]>> getByMonthlyRank3() {
		ArrayList<Map<String, Object[]>> list = dao.findByMonthRank3Store();
		return list;
	}
	
	
	// 일간 전체 매출
	public ArrayList<Map<String, Object[]>> getByDailyRank() {
		ArrayList<Map<String, Object[]>> list = dao.findByDayRankStore();
		return list;
	}
	
	// 월간 전체 매출
	public ArrayList<Map<String, Object[]>> getByMonthlyRank() {
		ArrayList<Map<String, Object[]>> list = dao.findByMonthRankStore();
		return list;
	}

	// 설정 기간 동안의 전체지점 연매출 조회
	public ArrayList<Map<String, Object[]>> getByYearlySales(int year1, int year2) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByYear(year1, year2);
		return list;
	}
	
	// 설정 기간 동안의 특정지점 연매출 조회
	public ArrayList<Map<String, Object[]>> getByStoreYearlySales(String storeid, int year1, int year2) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByStoreAndYear(storeid, year1, year2);
		return list;
	}
	
	// 전체 월매출 조회
	public ArrayList<Map<String, Object[]>> getByMonthSales(){
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByAllMonth();
		return list;
	}
	
	// 연도, 월별 지점 매출 조회
	public ArrayList<Map<String, Object[]>> getByYearAndMonthSales(String storeid, int year, int month){
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByYearAndMonth(storeid, year, month);
		return list;
	}
	
	// 일별 전체 매출 조회
	public ArrayList<Map<String, Object[]>> getByDailySales(){
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByAllDay();
		return list;
	}
}

