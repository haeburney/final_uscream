package com.example.uscream.selling;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface SellingDao extends JpaRepository<Selling, Integer> {
	
	
	// 일별 전체 매출
	@Transactional
    @Query(value="SELECT sellingdate, SUM(sellingprice) AS totalprice " 
            + "FROM Selling "
            + "GROUP BY sellingdate", nativeQuery=true)
	ArrayList<Map<String, Object[]>> findTotalPriceByAllDay();

	
	// 일별 전체 매출 (연,월 설정)
	@Transactional
	@Query(value = "SELECT sellingdate, SUM(sellingprice) AS totalprice FROM selling "
            + "WHERE EXTRACT(YEAR FROM sellingdate) = :year "
            + "AND EXTRACT(MONTH FROM sellingdate) = :month "
            + "GROUP BY sellingdate", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findTotalpriceByDay(
			@Param("year") int year, 
			@Param("month") int month);

	
	// 일별 전체 매출 (전체기간 스토어별)
	@Transactional
    @Query(value="SELECT se.storeid, st.storename, se.sellingdate, SUM(se.sellingprice) AS totalprice " 
            + "FROM Selling se " 
            + "JOIN Store st ON se.storeid = st.storeid " 
            + "WHERE se.storeid = :storeid " 
            + "GROUP BY se.sellingdate, st.storename, se.storeid", nativeQuery=true)
	ArrayList<Map<String, Object[]>> findTotalPriceByStoreAndAllDay(
	        @Param("storeid") String storeid);


	// 일별 전체 매출 (연,월,스토어별)
	@Transactional
    @Query(value="SELECT se.storeid, st.storename, se.sellingdate, SUM(se.sellingprice) AS totalprice " 
            + "FROM Selling se " 
            + "JOIN Store st ON se.storeid = st.storeid " 
            + "WHERE se.storeid = :storeid " 
            + "AND EXTRACT(YEAR FROM se.sellingdate) = :year " 
            + "AND EXTRACT(MONTH FROM se.sellingdate) = :month " 
            + "GROUP BY se.sellingdate, st.storename, se.storeid", nativeQuery=true)
	ArrayList<Map<String, Object[]>> findTotalPriceByStoreAndDay(
	        @Param("storeid") String storeid,
	        @Param("year") int year,
	        @Param("month") int month);
	
	
	// 월별 전체 매출
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM sellingdate) AS year, EXTRACT(MONTH FROM sellingdate) AS month, SUM(sellingprice) AS totalprice " 
	        + "FROM selling "
	        + "GROUP BY EXTRACT(YEAR FROM sellingdate), EXTRACT(MONTH FROM sellingdate)", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findTotalPriceByAllMonth();

//	@Transactional
//	@Query(value = "SELECT EXTRACT(YEAR FROM se.sellingdate) AS year, EXTRACT(MONTH FROM se.sellingdate) AS month, se.storeid, st.storename, SUM(se.sellingprice) AS totalprice " 
//	        + "FROM selling se "
//	        + "JOIN store st ON se.storeid = st.storeid "
//	        + "GROUP BY EXTRACT(YEAR FROM se.sellingdate), EXTRACT(MONTH FROM se.sellingdate), se.storeid, st.storename", nativeQuery = true)
//	ArrayList<Map<String, Object[]>> findTotalPriceByAllMonth();
	

	// 월별 전체 매출 (전체기간 스토어별 - id 혹은 스토명으로 검색) 
	@Transactional
	@Query(value = "SELECT se.storeid, st.storename, EXTRACT(YEAR FROM se.sellingdate) AS year, EXTRACT(MONTH FROM se.sellingdate) AS month, SUM(se.sellingprice) AS totalprice "
	        + "FROM selling se "
	        + "JOIN Store st ON se.storeid = st.storeid "
	        + "WHERE se.storeid = :keyword OR st.storename = :keyword "
	        + "GROUP BY se.storeid, st.storename, EXTRACT(YEAR FROM se.sellingdate), EXTRACT(MONTH FROM se.sellingdate)", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findTotalPriceByStoreAndAllMonth
			(@Param("keyword") String keyword);

	
	// 월별 전체 매출 (연도 설정) 
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM sellingdate) AS year, EXTRACT(MONTH FROM sellingdate) AS month, SUM(sellingprice) AS totalprice FROM selling "
	        + "WHERE EXTRACT(YEAR FROM sellingdate) = :year "
	        + "GROUP BY EXTRACT(YEAR FROM sellingdate), EXTRACT(MONTH FROM sellingdate)", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findTotalPriceByMonth(
	        @Param("year") int year);
	

	// 월별 전체 매출 (연도, 스토어별)
	@Transactional
	@Query(value = "SELECT se.storeid, st.storename, EXTRACT(MONTH FROM se.sellingdate) AS month, SUM(se.sellingprice) AS totalprice "
			+ "FROM selling se "
			+ "JOIN Store st ON se.storeid = st.storeid "
	        + "WHERE se.storeid = :storeid "
	        + "AND EXTRACT(YEAR FROM se.sellingdate) = :year "
	        + "GROUP BY se.storeid, st.storename, EXTRACT(MONTH FROM se.sellingdate)", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findTotalPriceByStoreAndMonth(
	        @Param("storeid") String storeid,
	        @Param("year") int year);
	
	
	// 월별 전체 매출 (연, 월, 스토어별)
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM se.sellingdate) AS year, EXTRACT(MONTH FROM se.sellingdate) AS month, se.storeid, st.storename, SUM(se.sellingprice) AS totalprice " +
	        "FROM Selling se " +
	        "JOIN Store st ON se.storeid = st.storeid " +
	        "WHERE se.storeid = :storeid " +
	        "  AND EXTRACT(YEAR FROM se.sellingdate) = :year " +
	        "  AND EXTRACT(MONTH FROM se.sellingdate) = :month " +
	        "GROUP BY EXTRACT(YEAR FROM se.sellingdate), EXTRACT(MONTH FROM se.sellingdate), se.storeid, st.storename", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findTotalPriceByYearAndMonth(
			@Param("storeid") String storeid,
	        @Param("year") int year,
	        @Param("month") int month);
	

	// 연도 전체 매출 (선택한 연도 사이의 모든 매출 조회)
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM sellingdate) AS year, SUM(sellingprice) AS totalprice " +
	        "FROM selling " +
	        "WHERE EXTRACT(YEAR FROM sellingdate) BETWEEN :startyear AND :endyear " +
	        "GROUP BY EXTRACT(YEAR FROM sellingdate)", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findTotalPriceByYear(
	        @Param("startyear") int year1,
	        @Param("endyear") int year2);

	
	// 연도 전체 매출 (선택한 연도 사이의 특정 지점 모든 매출 조회)
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM se.sellingdate) AS year, st.storename, SUM(se.sellingprice) AS totalprice " +
	        "FROM selling se " +
	        "JOIN store st ON se.storeid = st.storeid " +
	        "WHERE se.storeid = :storeid " +
	        "  AND EXTRACT(YEAR FROM se.sellingdate) BETWEEN :startyear AND :endyear " +
	        "GROUP BY EXTRACT(YEAR FROM se.sellingdate), st.storename", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findTotalPriceByStoreAndYear(
	        @Param("storeid") String storeid,
	        @Param("startyear") int year1,
	        @Param("endyear") int year2);
	
	
	// 전체 기간 일별 랭킹 전체
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
	ArrayList<Map<String, Object[]>> findByDayRankStore();
	
	
	// 전체 기간 월간 랭킹 전체
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
	ArrayList<Map<String, Object[]>> findByMonthRankStore();
	

	// 일간 매출 TOP3 지점
	@Transactional
	@Query(value = "SELECT r.sellingdate, r.storeid, r.storename, r.totalprice, r.rank " +
            "FROM (" +
            "   SELECT se.sellingdate, se.storeid, st.storename, " +
            "          SUM(se.sellingprice) AS totalprice, " +
            "          ROW_NUMBER() OVER (PARTITION BY se.sellingdate " +
            "                             ORDER BY SUM(se.sellingprice) DESC) AS rank " +
            "   FROM Selling se " +
            "   JOIN Store st ON se.storeid = st.storeid " +
            "   GROUP BY se.sellingdate, se.storeid, st.storename" +
            ") r " +
            "WHERE r.rank <= 3 " +
            "ORDER BY r.sellingdate, r.rank", nativeQuery = true) 
	ArrayList<Map<String, Object[]>> findByDayRank3Store();
	
	
	// 월간 매출 TOP3 지점
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
	ArrayList<Map<String, Object[]>> findByMonthRank3Store();
	
	
	@Transactional
    @Query(value = "SELECT r.sellingyear, r.sellingmonth, r.storeid, r.storename, r.totalprice, r.rank " +
            "FROM (" +
            "   SELECT EXTRACT(YEAR FROM se.sellingdate) AS sellingyear, EXTRACT(MONTH FROM se.sellingdate) AS sellingmonth, se.storeid, st.storename, " +
            "          SUM(se.sellingprice) AS totalprice, " +
            "          ROW_NUMBER() OVER (PARTITION BY EXTRACT(YEAR FROM se.sellingdate), EXTRACT(MONTH FROM se.sellingdate) " +
            "                             ORDER BY SUM(se.sellingprice) DESC) AS rank " +
            "   FROM Selling se " +
            "   JOIN Store st ON se.storeid = st.storeid " +
            "   WHERE EXTRACT(YEAR FROM se.sellingdate) = :year " +
            "   AND EXTRACT(MONTH FROM se.sellingdate) = :month " +
            "   GROUP BY EXTRACT(YEAR FROM se.sellingdate), EXTRACT(MONTH FROM se.sellingdate), se.storeid, st.storename" +
            ") r " +
            "WHERE r.rank <=5 " + 
            "ORDER BY r.sellingyear, r.sellingmonth, r.rank", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findByMonthRank5Store(
			@Param("year") int year,
			@Param("month") int month);
	

}


	






