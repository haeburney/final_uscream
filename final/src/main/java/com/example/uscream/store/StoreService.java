package com.example.uscream.store;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

	@Autowired
	private StoreDao dao;
	
	//	1. arraylist 담을 메서드 생성
	private ArrayList<StoreDto> change(ArrayList<Store> list){
		ArrayList<StoreDto> slist = new ArrayList<StoreDto>();
		for(Store entity:list) {
			slist.add(new StoreDto(entity.getStoreid(),entity.getManagername(),entity.getPwd(),entity.getManagername(),entity.getAccounttype(),entity.getStoreimg(),entity.getX(),entity.getY(),null));
		}
		return slist;
	}
	
	
	
	// 2. 지점(아이디) 생성, 수정
		public StoreDto save(StoreDto dto) {
		Store entity = dao.save(new Store(dto.getStoreid(), dto.getStorename(), dto.getPwd(), dto.getManagername(), dto.getAccounttype(),dto.getStoreimg(), dto.getX(), dto.getY()));
		return new StoreDto(entity.getStoreid(), entity.getStorename(), entity.getPwd(), entity.getManagername(),entity.getAccounttype(), entity.getStoreimg(),entity.getX(),entity.getY(),null);
	}
	
	
	// 3. 지점전체목록확인 
		public ArrayList<StoreDto> getAll() {
		ArrayList<Store> list = (ArrayList<Store>) dao.findAll();
		return change(list);
	}
	
	// 3.1 지점정보확인(1개_storeid로 검색)
		public StoreDto getById(String storeid) {
			Store s = dao.findById(storeid).orElse(null);
			if(s==null) {
			
				return null;
			}
			return new StoreDto(s.getStoreid(),s.getStorename(),s.getPwd(),s.getManagername(),s.getAccounttype(),s.getStoreimg(),s.getX(),s.getY(), null);
		}
	
	// 4. 지점정보확인(1개_지점명으로 검색)
		public ArrayList<StoreDto> getByStorename(String storename) {
		ArrayList<Store> entity = dao.findByStorenameLike("%"+storename+"%");
		ArrayList<StoreDto> slist = new ArrayList<StoreDto>();
		for(Store s: entity) {
			slist.add(new StoreDto(s.getStoreid(),s.getStorename(),s.getPwd(),s.getManagername(),s.getAccounttype(),s.getStoreimg(),s.getX(),s.getY(),null));
		}		
		return slist;
	}
		
		
		
	//지점정보확인(1개_지점명으로 검색)
//		public StoreDto getByStorename(String storename) {
//		Store entity = dao.findById(storename).orElse(null);
//		StoreDto dto = new StoreDto(entity.getStoreid(),entity.getStorename(),entity.getPwd(),entity.getManagername(),entity.getAccounttype(),entity.getStoreimg(),entity.getX(),entity.getY(),null));
//		return dto;
//		}		
	
	// 5. 지점 삭제
	public void deleteStore(String storeid) {
		dao.deleteById(storeid);
	}

	
}

