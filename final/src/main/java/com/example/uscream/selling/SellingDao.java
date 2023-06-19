
package com.example.uscream.selling;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.uscream.store.Store;

import jakarta.transaction.Transactional;

// 판매내역(매출) 테이블
// 1. 전체 지점 일별 총 매출
// 2. 전체 지점 월별 총 매출
// 3. 전체 지점 연도별 총 매출
// 4. 특정 지점 일별 총 매출
// 5. 특정 지점 월별 총 매출
// 6. 특정 지점 연도별 총 매출

@Repository
public interface SellingDao extends JpaRepository<Selling, Integer> {
	ArrayList<Selling> findByStoreid(Store storeid);
	ArrayList<Selling> fineByPeriodBetween(Date d1, Date d2);
	
	
	// 일별 전체지점 매출 (본사 매출관리 - 초기화면 캘린더에 하나 하나 넣어줄 거)
	@Transactional
	@Modifying
	@Query(value="select sellingdate, sum(sellingprice) as totalprice from selling group by sellingdate", nativeQuery=true)
	void getTotalpriceByDay();
	
	// 일별 특정지점 매출 (지점 매출관리 - 초기화면 캘린더에 하나 하나 넣어줄 거)
	@Transactional
	@Modifying
	@Query(value="select sellingdate, sum(sellingprice) as totalprice from selling where storeid=:storeid group by sellingdate", nativeQuery=true)
	void getTotalpriceByStoreAndDay(@Param("storeid") Store storeid);
	
	// 월별 전체지점 매출
	@Transactional
	@Modifying
	@Query(value="select sum(sellinprice) as totalprice from selling"
					+ "where extract(year from sellingdate) =: year"
					+ "and extract(month from sellingdate) = :month", nativeQuery=true)
	void getTotalpriceByMonth(@Param("year") int year, @Param("month") int month);



}

