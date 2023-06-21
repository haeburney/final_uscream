package com.example.uscream.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uscream.store.Store;
import com.example.uscream.store.StoreService;

@RestController 
@CrossOrigin(origins = "*")
@RequestMapping("/msg")
public class MsgController {
	
	private MsgService service;
	private StoreService storeservice;
	

	@Autowired
	public MsgController(MsgService service, StoreService storeservice) {
		super();
		this.service = service;
		this.storeservice = storeservice;
	}
	
	//글 작성
	@PostMapping("")
	public Map sendMsg(MsgDto dto) {
		service.save(dto);
		Map map = new HashMap();
		map.put(dto, dto);
		return map;
	}
	
	//인덱스 페이지 , 수신자로 검색
//	@GetMapping("")
//	public Map getIndex(@Param("id") String id) {
//		
//		Store store = new Store("aa","지점","123","매니저",2,"path",1,2);
//		
//		long CountByReceiver = service.countAll(store);
//		long CountByReceiverAndRead = service.countAllRead(store);
//		ArrayList<MsgDto> msglist = service.getByReceiver(store.getStoreid());
//
//		Map map = new HashMap();
//		map.put("CountByReceiver",CountByReceiver);
//		map.put("CountByReceiverAndRead",CountByReceiverAndRead);
//		map.put("msglist",msglist);
//		
//		return map;
//	}
	
	// 디테일 정보
	@GetMapping("/{msgnum}")
	public Map getdetail(@Param("msgnum") int num) {
		MsgDto msgdto = service.getMsg(num);
		Map map = new HashMap();
		map.put("msgdto", msgdto);

		return map;
	}
	
	// 즐겨찾기
	@PatchMapping("/mark/{msgnum}")
	public void changeMark(@Param("msgnum") int num) {
		service.changeMark(num);
	}
	
	// 안 읽음 처리
	@PatchMapping("/read/{msgnum}")
	public void changeRead(@Param("msgnum") int num) {
		service.changeReadcheck(num);
	}

	// 휴지통으로 보내기
	@PatchMapping("/del/{msgnum}")
	public void changeDelcheck(@Param("msgnum") int num) {
		service.changeDelcheck(num);
	}
	
	// 완전 삭제 
	@DeleteMapping("msgnum")
	public void deleteReal(@Param("msgnum") int num) {
		ArrayList<Integer> intlist = new ArrayList<Integer>();
		
		service.delete(num);
	}
	
	//임시보관 페이지 , 발신자로 조회 
	@GetMapping("{sender}")
	public Map getSecondPage(@Param("sender") String sender) {
			
		Map map = new HashMap();
			
		return map;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
