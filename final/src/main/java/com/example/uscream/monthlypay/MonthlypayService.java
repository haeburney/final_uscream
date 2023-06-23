package com.example.uscream.monthlypay;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.store.Store;

@Service
public class MonthlypayService {
	@Autowired
	MonthlypayDao dao;

	// 추가, 수정
	public MonthlypayDto save(MonthlypayDto dto) {
		Monthlypay entity = dao.save(
				new Monthlypay(dto.getMpnum(), dto.getEmp(), dto.getStoreid(), dto.getMpmonth(), dto.getMpsalary()));

		return new MonthlypayDto(entity.getMpnum(), entity.getEmp(), dto.getStoreid(), dto.getMpmonth(),
				dto.getMpsalary());
	}

	// 지점, 년월별 검색
	public ArrayList<MonthlypayDto> getByStoreAndMonth(String storeid, int year, int month) {
		ArrayList<MonthlypayDto> dtoList = new ArrayList<MonthlypayDto>();
		Store s = new Store(storeid, "", "", "", 0, "", 0, 0);
		ArrayList<Monthlypay> list = dao.findByStoreidAndMonth(year, month, storeid);
		for (Monthlypay vo : list) {
			dtoList.add(
					new MonthlypayDto(vo.getMpnum(), vo.getEmp(), vo.getStoreid(), vo.getMpmonth(), vo.getMpsalary()));
		}
		return dtoList;
	}

	// 전체 검색
	public ArrayList<MonthlypayDto> getByAll() {
		ArrayList<MonthlypayDto> dtoList = new ArrayList<MonthlypayDto>();
		ArrayList<Monthlypay> list = (ArrayList<Monthlypay>) dao.findAll();
		for (Monthlypay vo : list) {
			dtoList.add(
					new MonthlypayDto(vo.getMpnum(), vo.getEmp(), vo.getStoreid(), vo.getMpmonth(), vo.getMpsalary()));
		}

		return dtoList;
	}

	// 지점, 년별 검색
	public ArrayList<MonthlypayDto> getByStoreAndYear(String storeid, int year) {
		ArrayList<MonthlypayDto> dtoList = new ArrayList<MonthlypayDto>();
		Store s = new Store(storeid, "", "", "", 0, "", 0, 0);
		ArrayList<Monthlypay> list = dao.findByStoreidAndYear(year, storeid);
		for (Monthlypay vo : list) {
			dtoList.add(
					new MonthlypayDto(vo.getMpnum(), vo.getEmp(), vo.getStoreid(), vo.getMpmonth(), vo.getMpsalary()));
		}
		return dtoList;
	}

	// 삭제
	public void delete(int mpnum) {
		dao.deleteById(mpnum);
	}
}
