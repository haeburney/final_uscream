package com.example.uscream.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDao extends JpaRepository<Schedule, Integer> {
	// 아이디랑 날짜 비교하기 
	//ArrayList<Schedule> findByEmpAndStoreid(Emp emp, Store storeid);
	
}
