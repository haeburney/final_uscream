package com.example.uscream.monthlypay;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.uscream.store.Store;

import jakarta.transaction.Transactional;

public interface MonthlypayDao extends JpaRepository<Monthlypay, Integer> {
	//ArrayList<Monthlypay> findByStoreid(Store storeid); // 지점별 월급 가져오기
	
	@Transactional
	@Query(value="select * from monthlypay "
			+ "where extract(year from mpmonth) = :year "
			+ "and extract(month from mpmonth) = :month "
			+ "and storeid = :storeid", nativeQuery = true)
	ArrayList<Monthlypay> findByStoreidAndMonth(@Param("year") int year, @Param("month") int month, @Param("storeid") String storeid);

	@Transactional
	@Query(value="select * from monthlypay "
			+ "where extract(year from mpmonth) = :year "
			+ "and storeid = :storeid", nativeQuery = true)
	ArrayList<Monthlypay> findByStoreidAndYear(@Param("year") int year, @Param("storeid") String storeid);

	
}
