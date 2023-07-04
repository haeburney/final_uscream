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

	// 직접 추가, 수정
	public BasicscheduleDto save(BasicscheduleDto dto) {
		Basicschedule entity = dao.save(
				new Basicschedule(dto.getBsnum(),dto.getStoreid(), dto.getEmp(), dto.getBsdate(), dto.getStarttime(), dto.getEndtime(), dto.getStatus()));
		return new BasicscheduleDto(entity.getBsnum(), entity.getStoreid(), entity.getEmp(), entity.getBsdate(), entity.getStarttime(),
				entity.getEndtime(), entity.getStatus());
	}
	

	// num으로 (pk로) 검색 
	public BasicscheduleDto getByBsnum(int bsnum) {
		Basicschedule entity = dao.findById(bsnum).orElse(null);
		return new BasicscheduleDto(entity.getBsnum(), entity.getStoreid(), entity.getEmp(), entity.getBsdate(), entity.getStarttime(),
				entity.getEndtime(), entity.getStatus());
	}
	
	// 직원 번호로 검색 
	public ArrayList<BasicscheduleDto> getByEmp(int empnum, int status){
		Emp e = new Emp(empnum, null, "" ,null, null, "");
		ArrayList<Basicschedule> list = dao.findByEmpAndStatusOrderByBsdateDesc(e, status);
		ArrayList<BasicscheduleDto> dtoList =new ArrayList<BasicscheduleDto>();
		for(Basicschedule vo:list) {
			dtoList.add(new BasicscheduleDto(vo.getBsnum(), vo.getStoreid(), vo.getEmp(), vo.getBsdate(), vo.getStarttime(), vo.getEndtime(), vo.getStatus()));
		}
		return dtoList;
	}

	// 지점 아이디로 검색
	public ArrayList<BasicscheduleDto> getByStoreid(String storeid) {
		Store s = new Store(storeid, "", "", "", 0, "", 0, 0);
		ArrayList<Basicschedule> list = dao.findByStoreid(s);
		ArrayList<BasicscheduleDto> dtoList =new ArrayList<BasicscheduleDto>();
		for(Basicschedule vo:list) {
			dtoList.add(new BasicscheduleDto(vo.getBsnum(), vo.getStoreid(), vo.getEmp(), vo.getBsdate(), vo.getStarttime(), vo.getEndtime(), vo.getStatus()));
		}
		return dtoList;
	}
	
	// new
	// 직원 번호 + 달별로 검색
	public ArrayList<BasicscheduleDto> getByEmpAndMonth(int year, int month, int empnum){
		ArrayList<Basicschedule> list = dao.findByEmpAndMonth(year, month, empnum);
		ArrayList<BasicscheduleDto> dtoList =new ArrayList<BasicscheduleDto>();
		for(Basicschedule vo:list) {
			dtoList.add(new BasicscheduleDto(vo.getBsnum(), vo.getStoreid(), vo.getEmp(), vo.getBsdate(), vo.getStarttime(), vo.getEndtime(), vo.getStatus()));
		}
		return dtoList;
	}

	// 삭제
	public void delete(int bsnum) {
		dao.deleteById(bsnum);
	}
}
