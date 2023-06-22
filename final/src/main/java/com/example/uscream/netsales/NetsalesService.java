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
	
	// 각 테이블 참조 컬럼 저장(매출 페이지 로딩 시)
	public NetsalesDto save(NetsalesDto dto) {
//		dao.save(new Netsales(dto.getNetsalesnum(), dto.getStoreid(), dto.getSellingprice(), 
//				dto.getMpsalary(), dto.getOrdernum()));
		dao.save(new Netsales(dto.getNetsalesnum(), dto.getNetsalesdate(), dto.getStoreid(), 
				dto.getSellingprice(), dto.getMpsalary(),dto.getOrdercost()));
		
		return dto;
	}
	

}
