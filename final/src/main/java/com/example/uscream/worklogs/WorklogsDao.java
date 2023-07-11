package com.example.uscream.worklogs;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.uscream.emp.Emp;
import com.example.uscream.store.Store;

import jakarta.transaction.Transactional;

public interface WorklogsDao extends JpaRepository<Worklogs, Integer> {
	ArrayList<Worklogs> findByStoreidOrderByWdateDesc(Store storeid); // 지점마다 기록 불러오기

	ArrayList<Worklogs> findByEmpOrderByWdateDesc(Emp empnum); // 직원별 기록 불러오기

	// 달별로 불러오기 
	@Transactional
	@Query(value = "select sum(alltime), emp from worklogs "
			+ "where extract(year from endtime) = :year "
			+ "and extract(month from endtime) = :month "
			+ "group by emp"
			, nativeQuery = true)
	ArrayList<Object[]> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
	
	// 달별 + 스토어별로 불러오기 
	@Transactional
	@Query(value = "select sum(alltime), emp from worklogs "
			+ "where extract(year from endtime) = :year "
			+ "and extract(month from endtime) = :month "
			+ "and storeid = :storeid "
			+ "group by emp"
			, nativeQuery = true)
	ArrayList<Object[]> findByMonthAndStoreid(@Param("year") int year, @Param("month") int month,  @Param("storeid") String storeid);

	// 지각만 보기 + 멤버
	@Transactional
	@Query(value = "select * from worklogs where latetime > 0 and emp = :emp order by wdate desc", nativeQuery = true)
	ArrayList<Worklogs> findByLateTimeAndEmp(@Param("emp") int emp);
	
	// 초과만 보기 + 멤버
	@Transactional
	@Query(value = "select * from worklogs where latetime < 0 and emp = :emp order by wdate desc", nativeQuery = true)
	ArrayList<Worklogs> findByOverTimeAndEmp(@Param("emp")int emp);
	
	// 지각만 보기
	@Transactional
	@Query(value = "select * from worklogs where latetime > 0 and storeid = :storeid order by wdate desc", nativeQuery = true)
	ArrayList<Worklogs> findByLateTime( @Param("storeid") String storeid);
	
	// 초과만 보기
	@Transactional
	@Query(value = "select * from worklogs where latetime < 0 and storeid = :storeid order by wdate desc", nativeQuery = true)
	ArrayList<Worklogs> findByOverTime( @Param("storeid") String storeid);
}
