package com.example.uscream.schedule;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.uscream.emp.Emp;
import com.example.uscream.store.Store;

public interface ScheduleDao extends JpaRepository<Schedule, Integer> {
	ArrayList<Schedule> findByEmp(Emp empnum);							// 직원별 기본 스케줄 불러오기
	ArrayList<Schedule> findByStoreid(Store storeid);					// 지점마다 기본 스케줄 불러오기
	ArrayList<Schedule> findByEmpAndSdate(Emp empnum, LocalDate sdate);		// 날짜 & 직원별로 스케줄 불러오기
}

