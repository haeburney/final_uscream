package com.example.uscream.emp;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.uscream.store.Store;

public interface EmpDao extends JpaRepository<Emp, Integer> {
	 ArrayList<Emp> findByStoreid(Store storeid);	// 지점아이디별로 직원 불러오기
}
