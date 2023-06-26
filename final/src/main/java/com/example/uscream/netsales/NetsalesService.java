package com.example.uscream.netsales;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetsalesService {
	@Autowired
	private NetsalesDao dao;
	
	// 테이블에 값 update (매월 1일에 전월 데이터 저장)
	public NetsalesDto save(NetsalesDto dto) {
		dao.save(new Netsales(dto.getNetsalesnum(), dto.getNetsalesdate(), dto.getStoreid(), 
				dto.getMsellingprice(), dto.getMpsalary(),dto.getMordercost(), dto.getMnetsales()));
		
		return dto;
	} 

	// 전체 검색
	public ArrayList<NetsalesDto> getAll() {
		ArrayList<Netsales> list = (ArrayList<Netsales>) dao.findAll();
		ArrayList<NetsalesDto> list2 = new ArrayList<NetsalesDto>();
		for(Netsales vo : list) {
			list2.add(new NetsalesDto(vo.getNetsalesnum(), vo.getNetsalesdate(), vo.getStoreid(), 
					vo.getMsellingprice(), vo.getMpsalary(), vo.getMordercost(), vo.getMnetsales()));
			
		}
		return list2;
	}
	
	// 연도별 순매출
	public ArrayList<Map<String, Object[]>> getBySelectYear(String storeid, int year) {
		ArrayList<Map<String, Object[]>> list = dao.findNetsalesYear(storeid, year);
		return list;
	}
	
	// 월별 순매출
	public ArrayList<Map<String, Object[]>> getBySelectYearAndMonth(String storeid, int year, int month) {
		ArrayList<Map<String, Object[]>> list = dao.findYearAndMonth(storeid, year, month);
		return list;
	}
	

} 
