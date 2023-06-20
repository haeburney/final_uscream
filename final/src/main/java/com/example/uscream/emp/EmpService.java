package com.example.uscream.emp;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.store.Store;

@Service
public class EmpService {
	@Autowired
	private EmpDao dao;
	
	// 추가, 수정
	public EmpDto save(EmpDto dto) {
		Emp entity = dao.save(new Emp(dto.getEmpnum(), dto.getStoreid(), dto.getEmpname(), dto.getJoindate(), dto.getGrade(),dto.getColor()));
		dto.setEmpnum(entity.getEmpnum());
		dto.setStoreid(entity.getStoreid());
		dto.setEmpname(entity.getEmpname());
		dto.setJoindate(entity.getJoindate());
		dto.setGrade(entity.getGrade());
		dto.setColor(entity.getColor());
		return dto;
	}
	
	// 검색
	public EmpDto getById(int empnum) {
		Emp entity = dao.findById(empnum).orElse(null);
		EmpDto dto = new EmpDto(entity.getEmpnum(), entity.getStoreid(), entity.getEmpname(), entity.getJoindate(), entity.getGrade(), entity.getColor());
		return dto;
	}
	
	// 지점아이디별 검색 
//	public ArrayList<EmpDto> getByStoreId(Store storeid) {
//		ArrayList<Emp> list = dao.findByStoreId(storeid);
//		ArrayList<EmpDto> listDto = new ArrayList<>();
//		for(Emp e:list) {
//			listDto.add(new EmpDto(e.getEmpnum(),e.getStoreid(),e.getEmpname(),e.getJoindate(), e.getGrade(), e.getColor()));
//		}
//		return listDto;
//	}
	
	// 삭제 
//	public void delete(EmpDto dto) {
//		
//	}
}
