package com.example.uscream.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {
	@Autowired
	public GradeDao dao;
	
	// 등급 셀렉 한 정보 불러오기
	public GradeDto getSalary(int gnum) { 
		Grade g = dao.findById(gnum).orElse(null);
		return new GradeDto(g.getGrade(), g.getSalary());
	}
}
