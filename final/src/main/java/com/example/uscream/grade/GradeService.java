package com.example.uscream.grade;

import java.util.ArrayList;

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
	
	
	// 전체 정보 가져오기
	public ArrayList<GradeDto> getAll(){
		ArrayList<Grade> list = (ArrayList<Grade>)dao.findAll();
		ArrayList<GradeDto> dtoList = new ArrayList<GradeDto>();
		for(Grade vo:list) {
			dtoList.add(new GradeDto(vo.getGrade(), vo.getSalary()));
		}
		return dtoList;
	}
	
	// 값 넣기 
	public GradeDto save(GradeDto dto) {
		Grade entity = dao.save(new Grade(dto.getGnum(), dto.getSalary()));
		dto.setGnum(entity.getGrade());
		dto.setSalary(entity.getSalary());
		return dto;
	}
}
