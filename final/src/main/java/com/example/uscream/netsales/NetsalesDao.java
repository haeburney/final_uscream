package com.example.uscream.netsales;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface NetsalesDao extends JpaRepository<Netsales, Integer> {
	
	// 연도 선택하면 특정 지점의 모든 월의 순매출 검색하여 표출
	@Transactional
	@Query(value = "SELECT ne.storeid, st.storename, EXTRACT(YEAR FROM ne.netsalesdate) AS year, EXTRACT(MONTH FROM ne.netsalesdate) AS month, mnetsales "
			+ "FROM netsales ne "
			+ "JOIN Store st ON ne.storeid = st.storeid "
	        + "WHERE ne.storeid = :storeid "
	        + "AND EXTRACT(YEAR FROM ne.netsalesdate) = :year "
	        + "GROUP BY ne.storeid, st.storename, EXTRACT(YEAR FROM ne.netsalesdate), EXTRACT(MONTH FROM ne.netsalesdate), mnetsales", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findNetsalesYear(
	        @Param("storeid") String storeid,
	        @Param("year") int year);
	 
	
	// 연도 및 월 선택하면 특정 지점의 월 매출, 인건비, 발주금액, 순매출 검색하여 표출
	@Transactional
	@Query(value = "SELECT ne.storeid, st.storename, EXTRACT(YEAR FROM ne.netsalesdate) AS year, EXTRACT(MONTH FROM ne.netsalesdate) AS month, msellingprice, mpsalary, mordercost, mnetsales "
			+ "FROM netsales ne "
			+ "JOIN Store st ON ne.storeid = st.storeid "
	        + "WHERE ne.storeid = :storeid "
	        + "AND EXTRACT(YEAR FROM ne.netsalesdate) = :year "
	        + "AND EXTRACT(MONTH FROM ne.netsalesdate) = :month "
	        + "GROUP BY ne.storeid, st.storename, EXTRACT(YEAR FROM ne.netsalesdate), EXTRACT(MONTH FROM ne.netsalesdate), msellingprice, mpsalary, mordercost, mnetsales", nativeQuery = true)
	ArrayList<Map<String, Object[]>> findYearAndMonth(
	        @Param("storeid") String storeid,
	        @Param("year") int year,
			@Param("month") int month);
	
}
