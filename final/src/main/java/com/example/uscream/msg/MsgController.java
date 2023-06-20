package com.example.uscream.msg;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@CrossOrigin(origins = "*")
@RequestMapping("/msg")
public class MsgController {
	
	private MsgService service;
	
	
	
	@Autowired
	public MsgController(MsgService service) {
		super();
		this.service = service;
	}
	
	
	@PostMapping("")
	public Map sendMsg(MsgDto dto) {
		service.save(dto);
		Map map = new HashMap();
		map.put(dto, dto);
		return map;
	}
	
	//인덱스 페이지 , 수신자로 검색
	@GetMapping("")
	public Map getIndex(@Param("id") String id) {
		
		
		
		Map map = new HashMap();
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
