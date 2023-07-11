package com.example.uscream.basicschedule;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.uscream.emp.Emp;
import com.example.uscream.store.Store;

import jakarta.transaction.Transactional;

public interface BasicscheduleDao extends JpaRepository<Basicschedule, Integer> {
	ArrayList<Basicschedule> findByStoreid(Store storeid);	// 지점마다 기본 스케줄 불러오기
	ArrayList<Basicschedule> findByEmpAndStatusOrderByBsdateDesc(Emp empnum, int status);			// 직원별 기본 스케줄 불러오기
	
	@Transactional
	@Query(value = "select * from basicschedule "
			+ "where extract(year from bsdate) = :year "
			+ "and extract(month from bsdate) = :month "
			+ "and emp = :emp and status = 0 order by bsdate desc", nativeQuery = true)
	ArrayList<Basicschedule> findByEmpAndMonth(@Param("year") int year, @Param("month") int month, @Param("emp") int emp);
}
