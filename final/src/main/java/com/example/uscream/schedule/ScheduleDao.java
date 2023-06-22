package com.example.uscream.schedule;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.uscream.emp.Emp;
import com.example.uscream.store.Store;

public interface ScheduleDao extends JpaRepository<Schedule, Integer> {
	ArrayList<ScheduleDto> findByEmp(Emp empnum);				// 직원별 기본 스케줄 불러오기
	ArrayList<ScheduleDto> findByStoreid(Store storeid);	// 지점마다 기본 스케줄 불러오기
	ScheduleDto findByEmpAndSdate(Emp empnum, Date sdate);
}

