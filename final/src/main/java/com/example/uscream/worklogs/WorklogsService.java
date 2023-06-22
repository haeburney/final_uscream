package com.example.uscream.worklogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorklogsService {
	@Autowired
	WorklogsDao dao;
	
	// 추가, 수정
	public WorklogsDto save(WorklogsDto dto) {
		Worklogs entity = dao.save(new Worklogs(dto.getWnum(), dto.getEmp(), dto.getStoreid(), dto.getWdate(), dto.getStarttime(), dto.getEndtime(), dto.getAlltime(), dto.getLatetime()));
		return new WorklogsDto(entity.getWnum(),entity.getEmp(), entity.getStoreid(),entity.getWdate(), entity.getStarttime(), entity.getEndtime(), entity.getAlltime(), entity.getLatetime());
	}
	
	// num으로 (pk로) 검색
//	public WorklogsDto getByWnum(int wnum) {
//		Worklogs entity = dao.findById(wnum).orElse(null);
//		
//	}
}
