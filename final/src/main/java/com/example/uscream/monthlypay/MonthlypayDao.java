package com.example.uscream.monthlypay;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlypayDao extends JpaRepository<Monthlypay, Integer> {
	//ArrayList<Monthlypay> findByStoreid(Store storeid); // 지점별 월급 가져오기
}
