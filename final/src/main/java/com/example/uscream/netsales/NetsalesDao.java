package com.example.uscream.netsales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetsalesDao extends JpaRepository<Netsales, Integer> {

	// 순매출의 경우 일별 매출은 산출하지 않음 (순매출 = 판매내역 - (인건비 + 발주신청))
	
	// 1. 매출 페이지 진입 시 참조 테이블의 값을 불러옴
	// 2. 1번으로 순매출 계산
	// 3. 특정 지점의 월별 순매출 산출
	// 4. 특정 지점의 연도별 순매출 산출
	

}
