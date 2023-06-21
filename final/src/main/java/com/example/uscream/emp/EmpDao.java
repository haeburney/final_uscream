package com.example.uscream.emp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpDao extends JpaRepository<Emp, Integer> {
	// ArrayList<Emp> findByStore(Store store);	// 지점아이디별로 직원 불러오기
}
