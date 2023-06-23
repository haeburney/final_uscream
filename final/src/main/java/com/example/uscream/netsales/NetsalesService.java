package com.example.uscream.netsales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.monthlypay.Monthlypay;
import com.example.uscream.porder.Porder;
import com.example.uscream.selling.Selling;
import com.example.uscream.store.Store;

@Service
public class NetsalesService {
	@Autowired
	private NetsalesDao dao;
	
	// 테이블에 값 update (매월 1일에 전월 데이터 가져옴)
	public NetsalesDto save(NetsalesDto dto) {
		dao.save(new Netsales(dto.getNetsalesnum(), dto.getNetsalesdate(), dto.getStoreid(), 
				dto.getMsellingprice(), dto.getMpsalary(),dto.getMordercost(), dto.getMnetsales()));
		
		return dto;
	}

} 
