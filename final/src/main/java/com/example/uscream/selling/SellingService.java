package com.example.uscream.selling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.uscream.sellproduct.SellProduct;
import com.example.uscream.store.Store;

@Service
public class SellingService {
	@Autowired
	private SellingDao dao;
 
	 
	// 판매내역 저장  
	public SellingDto save(String sotreid,int sellproductnum,int cnt) {
	      Store store = new Store(sotreid, "", "", "", 0, "", 0, 0);
	      System.out.println(store);
	      SellProduct sellproduct = new SellProduct(sellproductnum,"",0);
	      System.out.println(sellproduct);
	      Selling entity = dao.save(new Selling(0, sellproduct,store,null,cnt, 0));
	      SellingDto dto = new SellingDto(entity.getSellingnum(),entity.getSellproduct(),entity.getStoreid(),entity.getSellingdate(),entity.getSellingcnt(),entity.getSellingprice());
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
	public ArrayList<Map<String, Object[]>> getByDailySales(){
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByAllDay();
		return list;
	}
	

	// 일별 전체 매출 (연,월 설정)
	public ArrayList<Map<String, Object[]>> getByDailySales2(int year, int month){
		ArrayList<Map<String, Object[]>> list = dao.findTotalpriceByDay(year, month);
		System.out.println(list);
		return list;
	}
	
	
	// 일별 전체 매출 (전체기간 스토어별)
	public ArrayList<Map<String, Object[]>> getByStoreAllDaySales(String storeid) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByStoreAndAllDay(storeid);
		return list;
	}
	
	
	// 일별 전체 매출 (연,월,스토어별)
	public ArrayList<Map<String, Object[]>> getByStoreDailySales(String storeid, int year, int month) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByStoreAndDay(storeid, year, month);
		return list;
	}
	
	
	// 월별 전체 매출
	public ArrayList<Map<String, Object[]>> getByMonthSales(){
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByAllMonth();
		return list;
	}
	
	
	// 월별 전체 매출 (전체기간 스토어별) 
	public ArrayList<Map<String, Object[]>> getByStoreAllMonthlySales(String storeid, String storename){
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByStoreAndAllMonth(storeid, storename);
		return list;
	}

		
	// 월별 전체 매출 (연도 설정) 
	public ArrayList<Map<String, Object[]>> getByMonthlySales(int year){
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByMonth(year);
		return list;
	}
	
	
	// 월별 전체 매출 (연도, 스토어별)
	public ArrayList<Map<String, Object[]>> getByStoreMonthlySales(String storeid, int year){
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByStoreAndMonth(storeid, year);
		return list;
	}
	
	
	
	// 월별 전체 매출 (연, 월, 스토어별)
	public ArrayList<Map<String, Object[]>> getByYearAndMonthSales(String storeid, int year, int month){
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByYearAndMonth(storeid, year, month);
		return list;
	}
	

	// 연도 전체 매출 (선택한 연도 사이의 모든 매출 조회)
	public ArrayList<Map<String, Object[]>> getByYearlySales(int year1, int year2) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByYear(year1, year2);
		return list;
	}
	
	
	// 연도 전체 매출 (선택한 연도 사이의 특정 지점 모든 매출 조회)
	public ArrayList<Map<String, Object[]>> getByStoreYearlySales(String storeid, int year1, int year2) {
		ArrayList<Map<String, Object[]>> list = dao.findTotalPriceByStoreAndYear(storeid, year1, year2);
		return list;
	}
	
	
	// 전체 기간 일별 랭킹 전체
	public ArrayList<Map<String, Object[]>> getByDailyRank() {
		ArrayList<Map<String, Object[]>> list = dao.findByDayRankStore();
		return list;
	}
	
	
	// 전체 기간 월간 랭킹 전체
	public ArrayList<Map<String, Object[]>> getByMonthlyRank() {
		ArrayList<Map<String, Object[]>> list = dao.findByMonthRankStore();
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

}

