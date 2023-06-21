package com.example.uscream.selling;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.uscream.store.Store;

import jakarta.transaction.Transactional;

@Repository
public interface SellingDao extends JpaRepository<Selling, Integer> {

	@Transactional
	@Query(value = "SELECT sellingdate, SUM(sellingprice) AS totalprice FROM selling "
            + "WHERE EXTRACT(YEAR FROM sellingdate) = :year "
            + "AND EXTRACT(MONTH FROM sellingdate) = :month "
            + "GROUP BY sellingdate", nativeQuery = true)
	ArrayList<Object[]> findTotalpriceByDay(
			@Param("year") int year, 
			@Param("month") int month);

	
	// 연도 및 월 선택하면 해당 월의 특점 지점 일별 매출 검색하여 표출
	@Transactional
	@Query(value = "SELECT sellingdate, SUM(sellingprice) AS totalprice FROM selling "
	        + "WHERE storeid = :storeid "
	        + "AND EXTRACT(YEAR FROM sellingdate) = :year "
	        + "AND EXTRACT(MONTH FROM sellingdate) = :month "
	        + "GROUP BY sellingdate", nativeQuery = true)
	ArrayList<Object[]> findTotalPriceByStoreAndDay(
	        @Param("storeid") Store storeid,
	        @Param("year") int year,
	        @Param("month") int month);
	
	
	// 연도 선택하면 모든 월 매출 검색하여 표출
	@Transactional
	@Query(value = "SELECT EXTRACT(MONTH FROM sellingdate) AS month, SUM(sellingprice) AS totalprice FROM selling "
	        + "WHERE EXTRACT(YEAR FROM sellingdate) = :year "
	        + "GROUP BY EXTRACT(MONTH FROM sellingdate)", nativeQuery = true)
	ArrayList<Object[]> findTotalPriceByMonth(
	        @Param("year") int year);
	
	
	// 연도 선택하면 특정 지점의 모든 월 매출 검색하여 표출
	@Transactional
	@Query(value = "SELECT EXTRACT(MONTH FROM sellingdate) AS month, SUM(sellingprice) AS totalprice FROM selling "
	        + "WHERE storeid = :storeid "
	        + "AND EXTRACT(YEAR FROM sellingdate) = :year "
	        + "GROUP BY EXTRACT(MONTH FROM sellingdate)", nativeQuery = true)
	ArrayList<Object[]> findTotalPriceByStoreAndMonth(
	        @Param("storeid") int storeid,
	        @Param("year") int year);
	
	
	// 일간 매출 TOP3 지점 (매출액, 지점명 함께 표출) 
	@Query(value = "SELECT sellingdate, store.storename, totalprice "
	        + "FROM (SELECT sellingdate, storeid, SUM(sellingprice) AS totalprice, "
	        + "ROW_NUMBER() OVER (PARTITION BY sellingdate ORDER BY SUM(sellingprice) DESC) AS rank "
	        + "FROM selling "
	        + "GROUP BY sellingdate, storeid) ranked "
	        + "WHERE rank <= 3 "
	        + "ORDER BY sellingdate, rank", nativeQuery = true)
	ArrayList<Object[]> findByDayRank3Store();
	
	
	// 월간 매출 TOP3 지점 (매출액, 지점명 함께 표출)
	@Query(value = "SELECT sellingmonth, store.storename, totalprice " +
	        "FROM (SELECT EXTRACT(MONTH FROM sellingdate) AS sellingmonth, storeid, " +
	        "             SUM(sellingprice) AS totalprice, " +
	        "             ROW_NUMBER() OVER (PARTITION BY EXTRACT(MONTH FROM sellingdate) " +
	        "                                ORDER BY SUM(sellingprice) DESC) AS rank " +
	        "      FROM selling " +
	        "      GROUP BY EXTRACT(MONTH FROM sellingdate), storeid) ranked " +
	        "WHERE rank <= 3 " +
	        "ORDER BY sellingmonth, rank", nativeQuery = true)
	ArrayList<Object[]> findByMonthRank3Store();
	

	// 시작연도 및 끝연도 선택하면 해당 구간 동안의 전체지점 연매출 조회
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM sellingdate) AS year, SUM(sellingprice) AS totalprice " +
	        "FROM selling " +
	        "WHERE EXTRACT(YEAR FROM sellingdate) BETWEEN :startyear AND :endyear " +
	        "GROUP BY EXTRACT(YEAR FROM sellingdate)", nativeQuery = true)
	ArrayList<Object[]> findTotalPriceByYear(
	        @Param("startyear") int year1,
	        @Param("endyear") int year2);

	
	// 시작연도 및 끝연도 선택하면 해당 구간 동안의 특정지점 연매출 조회
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM sellingdate) AS year, SUM(sellingprice) AS totalprice " +
	        "FROM selling " +
	        "WHERE storeid = :storeid " +
	        "AND EXTRACT(YEAR FROM sellingdate) BETWEEN :startyear AND :endyear " +
	        "GROUP BY EXTRACT(YEAR FROM sellingdate)", nativeQuery = true)
	ArrayList<Object[]> findTotalPriceByStoreAndYear(
	        @Param("storeid") Store storeid,
	        @Param("startyear") int year1,
	        @Param("endyear") int year2);
}

