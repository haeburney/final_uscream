
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

	// 연도 및 월 선택하면 해당 월의 전체 일별 매출 검색하여 표출
	@Transactional
	@Query(value="select sellingdate, sum(sellingprice) as totalprice from selling"
			+ "where extract(year from sellingdate) = :year"
			+ "and extract(month from sellingdate) = :month"
			+ "group by sellingdate", nativeQuery=true)
	ArrayList<Selling> findTotalpriceByDay(
			@Param("year") int year, 
			@Param("month") int month);

	
	// 연도 및 월 선택하면 해당 월의 특점 지점 일별 매출 검색하여 표출
	@Transactional
	@Query(value="select sellingdate, sum(sellingprice) as totalprice from selling"
			+ "where storeid = :storeid"
			+ "and extract(year from sellingdate) = :year"
			+ "and extract(month from sellingdate) = :month"
			+ "group by sellingdate", nativeQuery=true)
	ArrayList<Selling> findTotalpriceByStoreAndDay(
			@Param("storeid") Store storeid, 
			@Param("year") int year, 
			@Param("month") int month);
	
	
	// 연도 선택하면 모든 월 매출 검색하여 표출
	@Transactional
	@Query(value="select extract(month from sellingdate) as month, sum(sellingprice) as totalprice from selling"
			+ "where extract(year from sellingdate) = :year"
			+ "group by extract(month from sellingdate)", nativeQuery=true)
	ArrayList<Selling> findTotalPriceByMonth(
			@Param("year") int year);
	
	
	// 연도 선택하면 특정 지점의 모든 월 매출 검색하여 표출
	@Transactional
	@Query(value="select extract(month from sellingdate) as month, sum(sellingprice) as totalprice from selling"
			+ "where storeid = :storeid"
			+ "and extract(year from sellingdate) = :year"
			+ "group by extract(month from sellingdate)", nativeQuery=true)
	ArrayList<Selling> findTotalPriceByStoreAndMonth(
			@Param("storeid") Store storeid,
			@Param("year") int year);
	
	
	// 일간 매출 TOP3 지점 (매출액, 지점명 함께 표출) 
	@Query(value="select sellingdate, store.storebranch , totalprice"
			+ "from (select sellingdate, storeid, sum(sellingprice) as totalprice,"
			+ "row_number() over (partition by sellingdate order by sum(sellingprice) desc) as rank"
			+ "from selling"
			+ "group by sellingdate, storeid) ranked"
			+ "where rank <=3"
			+ "order by sellingdate, rank", nativeQuery=true)
	ArrayList<Selling> findByDayRank3Store();
	
	
	// 월간 매출 TOP3 지점 (매출액, 지점명 함께 표출)
	@Query(value="SELECT sellingmonth, store.storebranch, totalprice " +
	        "FROM (SELECT EXTRACT(MONTH FROM sellingdate) AS sellingmonth, storeid, " +
	        "             SUM(sellingprice) AS totalprice, " +
	        "             ROW_NUMBER() OVER (PARTITION BY EXTRACT(MONTH FROM sellingdate) " +
	        "                                ORDER BY SUM(sellingprice) DESC) AS rank " +
	        "      FROM selling " +
	        "      GROUP BY EXTRACT(MONTH FROM sellingdate), storeid) ranked " +
	        "WHERE rank <= 3 " +
	        "ORDER BY sellingmonth, rank", nativeQuery=true)
	ArrayList<Selling> findByMonthRank3Store();
	

	// 시작연도 및 끝연도 선택하면 해당 구간 동안의 전체지점 연매출 조회
	@Transactional
	@Query(value="select extract(year from sellingdate) as year, sum(sellingprice) as totalprice from selling"
			+ "where extract(year from sellingdate) between :startyear and :end year"
			+ "group by extract(year from sellingdate)", nativeQuery=true)
	ArrayList<Selling> findTotalPriceByYear(
			@Param("startyear") int year1,
			@Param("endyear") int year2);

	
	// 시작연도 및 끝연도 선택하면 해당 구간 동안의 특정지점 연매출 조회
	@Transactional
	@Query(value="select extract(year from sellingdate) as year, sum(sellingprice) as totalprice from selling"
			+ "where storeid = :storeid"
			+ "and extract(year from sellingdate) between :startyear and :end year"
			+ "group by extract(year from sellingdate)", nativeQuery=true)
	ArrayList<Selling> findTotalPriceByStoreAndYear(
			@Param("storeid") Store storeid,
			@Param("startyear") int year1,
			@Param("endyear") int year2);
	

}


