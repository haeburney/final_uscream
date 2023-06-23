package com.example.uscream.basicschedule;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.uscream.emp.Emp;
import com.example.uscream.store.Store;

public interface BasicscheduleDao extends JpaRepository<Basicschedule, Integer> {
	ArrayList<Basicschedule> findByStoreid(Store storeid);	// 지점마다 기본 스케줄 불러오기
	ArrayList<Basicschedule> findByEmp(Emp empnum);			// 직원별 기본 스케줄 불러오기
	
}
