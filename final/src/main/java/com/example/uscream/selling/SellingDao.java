package com.example.uscream.selling;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface SellingDao extends JpaRepository<Selling, Integer> {

	// 연도 및 월 선택하면 해당 월의 전체 일별 매출 검색하여 표출
	@Transactional
	@Query(value = "SELECT sellingdate, SUM(sellingprice) AS totalprice FROM selling "
            + "WHERE EXTRACT(YEAR FROM sellingdate) = :year "
            + "AND EXTRACT(MONTH FROM sellingdate) = :month "
            + "GROUP BY sellingdate", nativeQuery = true)
	ArrayList<Object[]> findTotalpriceByDay(
			@Param("year") int year, 
			@Param("month") int month);


	// 연도 및 월 선택하면 해당 월의 특정 지점 일별 매출 검색하여 표출 *
	@Transactional
    @Query(value="SELECT se.storeid, st.storename, se.sellingdate, SUM(se.sellingprice) AS totalprice " 
            + "FROM Selling se " 
            + "JOIN Store st ON se.storeid = st.storeid " 
            + "WHERE se.storeid = :storeid " 
            + "AND EXTRACT(YEAR FROM se.sellingdate) = :year " 
            + "AND EXTRACT(MONTH FROM se.sellingdate) = :month " 
            + "GROUP BY se.sellingdate, st.storename, se.storeid", nativeQuery=true)
	ArrayList<Object[]> findTotalPriceByStoreAndDay(
	        @Param("storeid") String storeid,
	        @Param("year") int year,
	        @Param("month") int month);
	
	
	// 연도 선택하면 모든 월 매출 검색하여 표출
	@Transactional
	@Query(value = "SELECT EXTRACT(MONTH FROM sellingdate) AS month, SUM(sellingprice) AS totalprice FROM selling "
	        + "WHERE EXTRACT(YEAR FROM sellingdate) = :year "
	        + "GROUP BY EXTRACT(MONTH FROM sellingdate)", nativeQuery = true)
	ArrayList<Object[]> findTotalPriceByMonth(
	        @Param("year") int year);
	
	
	// 연도 선택하면 특정 지점의 모든 월 매출 검색하여 표출 *
	@Transactional
	@Query(value = "SELECT se.storeid, st.storename, EXTRACT(MONTH FROM se.sellingdate) AS month, SUM(se.sellingprice) AS totalprice "
			+ "FROM selling se "
			+ "JOIN Store st ON se.storeid = st.storeid "
	        + "WHERE se.storeid = :storeid "
	        + "AND EXTRACT(YEAR FROM se.sellingdate) = :year "
	        + "GROUP BY se.storeid, st.storename, EXTRACT(MONTH FROM se.sellingdate)", nativeQuery = true)
	ArrayList<Object[]> findTotalPriceByStoreAndMonth(
	        @Param("storeid") String storeid,
	        @Param("year") int year);
	
	
	// 일간 매출 TOP3 지점 (매출액, 지점명 함께 표출) *
	@Transactional
	@Query(value = "SELECT r.sellingmonth, r.sellingday, r.storeid, r.storename, r.totalprice, r.rank " +
            "FROM (" +
            "   SELECT EXTRACT(MONTH FROM se.sellingdate) AS sellingmonth, EXTRACT(DAY FROM se.sellingdate) AS sellingday, se.storeid, st.storename, " +
            "          SUM(se.sellingprice) AS totalprice, " +
            "          ROW_NUMBER() OVER (PARTITION BY EXTRACT(MONTH FROM se.sellingdate), EXTRACT(DAY FROM se.sellingdate) " +
            "                             ORDER BY SUM(se.sellingprice) DESC) AS rank " +
            "   FROM Selling se " +
            "   JOIN Store st ON se.storeid = st.storeid " +
            "   GROUP BY EXTRACT(MONTH FROM se.sellingdate), EXTRACT(DAY FROM se.sellingdate), se.storeid, st.storename" +
            ") r " +
            "WHERE r.rank <= 3 " +
            "ORDER BY r.sellingmonth, r.sellingday, r.rank", nativeQuery = true)
	ArrayList<Object[]> findByDayRank3Store();
	
	
	// 월간 매출 TOP3 지점 (매출액, 지점명 함께 표출) *
	@Transactional
    @Query(value = "SELECT r.sellingyear, r.sellingmonth, r.storeid, r.storename, r.totalprice, r.rank " +
            "FROM (" +
            "   SELECT EXTRACT(YEAR FROM se.sellingdate) AS sellingyear, EXTRACT(MONTH FROM se.sellingdate) AS sellingmonth, se.storeid, st.storename, " +
            "          SUM(se.sellingprice) AS totalprice, " +
            "          ROW_NUMBER() OVER (PARTITION BY EXTRACT(YEAR FROM se.sellingdate), EXTRACT(MONTH FROM se.sellingdate) " +
            "                             ORDER BY SUM(se.sellingprice) DESC) AS rank " +
            "   FROM Selling se " +
            "   JOIN Store st ON se.storeid = st.storeid " +
            "   GROUP BY EXTRACT(YEAR FROM se.sellingdate), EXTRACT(MONTH FROM se.sellingdate), se.storeid, st.storename" +
            ") r " +
            "WHERE r.rank <= 3 " +
            "ORDER BY r.sellingyear, r.sellingmonth, r.rank", nativeQuery = true)
	ArrayList<Object[]> findByMonthRank3Store();
	

	// 일간 매출 랭크 (매출액, 지점명 함께 표출) *
	@Transactional
	@Query(value = "SELECT r.sellingmonth, r.sellingday, r.storeid, r.storename, r.totalprice, r.rank " +
            "FROM (" +
            "   SELECT EXTRACT(MONTH FROM se.sellingdate) AS sellingmonth, EXTRACT(DAY FROM se.sellingdate) AS sellingday, se.storeid, st.storename, " +
            "          SUM(se.sellingprice) AS totalprice, " +
            "          ROW_NUMBER() OVER (PARTITION BY EXTRACT(MONTH FROM se.sellingdate), EXTRACT(DAY FROM se.sellingdate) " +
            "                             ORDER BY SUM(se.sellingprice) DESC) AS rank " +
            "   FROM Selling se " +
            "   JOIN Store st ON se.storeid = st.storeid " +
            "   GROUP BY EXTRACT(MONTH FROM se.sellingdate), EXTRACT(DAY FROM se.sellingdate), se.storeid, st.storename" +
            ") r " +
            "ORDER BY r.sellingmonth, r.sellingday, r.rank", nativeQuery = true)
	ArrayList<Object[]> findByDayRankStore();
	
	
	// 월간 매출 랭크 (매출액, 지점명 함께 표출) *
	@Transactional
    @Query(value = "SELECT r.sellingyear, r.sellingmonth, r.storeid, r.storename, r.totalprice, r.rank " +
            "FROM (" +
            "   SELECT EXTRACT(YEAR FROM se.sellingdate) AS sellingyear, EXTRACT(MONTH FROM se.sellingdate) AS sellingmonth, se.storeid, st.storename, " +
            "          SUM(se.sellingprice) AS totalprice, " +
            "          ROW_NUMBER() OVER (PARTITION BY EXTRACT(YEAR FROM se.sellingdate), EXTRACT(MONTH FROM se.sellingdate) " +
            "                             ORDER BY SUM(se.sellingprice) DESC) AS rank " +
            "   FROM Selling se " +
            "   JOIN Store st ON se.storeid = st.storeid " +
            "   GROUP BY EXTRACT(YEAR FROM se.sellingdate), EXTRACT(MONTH FROM se.sellingdate), se.storeid, st.storename" +
            ") r " +
            "ORDER BY r.sellingyear, r.sellingmonth, r.rank", nativeQuery = true)
	ArrayList<Object[]> findByMonthRankStore();
	

	// 시작연도 및 끝연도 선택하면 해당 구간 동안의 전체지점 연매출 조회
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM sellingdate) AS year, SUM(sellingprice) AS totalprice " +
	        "FROM selling " +
	        "WHERE EXTRACT(YEAR FROM sellingdate) BETWEEN :startyear AND :endyear " +
	        "GROUP BY EXTRACT(YEAR FROM sellingdate)", nativeQuery = true)
	ArrayList<Object[]> findTotalPriceByYear(
	        @Param("startyear") int year1,
	        @Param("endyear") int year2);

	
	// 시작연도 및 끝연도 선택하면 해당 구간 동안의 특정지점 연매출 조회 **
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM se.sellingdate) AS year, st.storename, SUM(se.sellingprice) AS totalprice " +
	        "FROM selling se " +
	        "JOIN store st ON se.storeid = st.storeid " +
	        "WHERE se.storeid = :storeid " +
	        "  AND EXTRACT(YEAR FROM se.sellingdate) BETWEEN :startyear AND :endyear " +
	        "GROUP BY EXTRACT(YEAR FROM se.sellingdate), st.storename", nativeQuery = true)
	ArrayList<Object[]> findTotalPriceByStoreAndYear(
	        @Param("storeid") String storeid,
	        @Param("startyear") int year1,
	        @Param("endyear") int year2);
}


