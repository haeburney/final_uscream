package com.example.uscream.worklogs;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.basicschedule.BasicscheduleDto;
import com.example.uscream.emp.Emp;
import com.example.uscream.store.Store;

@Service
public class WorklogsService {
	@Autowired
	WorklogsDao dao;

	// 추가, 수정
	public WorklogsDto save(WorklogsDto dto) {
		Worklogs entity = dao.save(new Worklogs(dto.getWnum(), dto.getEmp(), dto.getStoreid(), dto.getWdate(),
				dto.getStarttime(), dto.getEndtime(), dto.getAlltime(), dto.getLatetime()));
		return new WorklogsDto(entity.getWnum(), entity.getEmp(), entity.getStoreid(), entity.getWdate(),
				entity.getStarttime(), entity.getEndtime(), entity.getAlltime(), entity.getLatetime());
	}

	// num으로 (pk로) 검색
	public WorklogsDto getByWnum(int wnum) {
		Worklogs entity = dao.findById(wnum).orElse(null);
		return new WorklogsDto(entity.getWnum(), entity.getEmp(), entity.getStoreid(), entity.getWdate(),
				entity.getStarttime(), entity.getEndtime(), entity.getAlltime(), entity.getLatetime());
	}

	// 직원 번호로 검색
	public ArrayList<WorklogsDto> getByEmp(int empnum) {
		Emp e = new Emp(empnum, null, "", null, null, "");
		ArrayList<Worklogs> list = dao.findByEmp(e);
		ArrayList<WorklogsDto> dtoList = new ArrayList<WorklogsDto>();
		for (Worklogs vo : list) {
			dtoList.add(new WorklogsDto(vo.getWnum(), vo.getEmp(), vo.getStoreid(), vo.getWdate(), vo.getStarttime(),
					vo.getEndtime(), vo.getAlltime(), vo.getLatetime()));
		}

		return dtoList;
	}

	// 지점 아이디로 검색
	public ArrayList<WorklogsDto> getByStoreid(String storeid) {
		Store s = new Store(storeid, "", "", "", 0, "", 0, 0);
		ArrayList<Worklogs> list = dao.findByStoreidOrderByWdateDesc(s);
		ArrayList<WorklogsDto> dtoList = new ArrayList<WorklogsDto>();
		for (Worklogs vo : list) {
			dtoList.add(new WorklogsDto(vo.getWnum(), vo.getEmp(), vo.getStoreid(), vo.getWdate(), vo.getStarttime(),
					vo.getEndtime(), vo.getAlltime(), vo.getLatetime()));
		}

		return dtoList;
	}

	// 삭제
	public void delete(int wnum) {
		dao.deleteById(wnum);
	}

	// 달별로 리스트 불러오기
	// 될지 안될지 모르겠어...되네..?응응..
	public ArrayList<Object[]> getByYearAndMonth(int year, int month) {
		ArrayList<Object[]> list = dao.findByYearAndMonth(year, month);

		return list;
	}

	// 달별 & storeid 별로 리스트 불러오기
	public ArrayList<Object[]> getByMonthAndStoreid(int year, int month, String storeid) {
		ArrayList<Object[]> list = dao.findByMonthAndStoreid(year, month, storeid);

		return list;
	}
}
