package com.example.uscream.basicschedule;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.emp.Emp;
import com.example.uscream.store.Store;

@Service
public class BasicscheduleService {
	@Autowired
	BasicscheduleDao dao;

	// 추가, 수정
	public BasicscheduleDto save(BasicscheduleDto dto) {
		Basicschedule entity = dao.save(
				new Basicschedule(dto.getBsnum(),dto.getStoreid(), dto.getEmp(), dto.getBsdate(), dto.getStarttime(), dto.getEndtime()));
		return new BasicscheduleDto(entity.getBsnum(), entity.getStoreid(), entity.getEmp(), entity.getBsdate(), entity.getStarttime(),
				entity.getEndtime());
	}

	// num으로 (pk로) 검색 
	public BasicscheduleDto getByBsnum(int bsnum) {
		Basicschedule entity = dao.findById(bsnum).orElse(null);
		return new BasicscheduleDto(entity.getBsnum(), entity.getStoreid(), entity.getEmp(), entity.getBsdate(), entity.getStarttime(),
				entity.getEndtime());
	}
	
	// 직원 번호로 검색 
	public ArrayList<BasicscheduleDto> getByEmp(int empnum){
		Emp e = new Emp(empnum, null, "" ,null, null, "");
		ArrayList<BasicscheduleDto> list = dao.findByEmp(e);
		
		return list;
	}

	// 지점 아이디로 검색
	public ArrayList<BasicscheduleDto> getByStoreid(String storeid) {
		Store s = new Store(storeid, "", "", "", 0, "", 0, 0);
		ArrayList<BasicscheduleDto> list = dao.findByStoreid(s);
		return list;
	}

	// 삭제
	public void delete(int bsnum) {
		dao.deleteById(bsnum);
	}
}
