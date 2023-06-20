package com.example.uscream.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notices")
public class NoticeController {

	@Autowired
	private NoticeService service;
	
	@Value("${spring.servlet.multipart.location}")
	private String path;  //C:/img/shop
	
	@PostMapping()
	public Map add(NoticeDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		NoticeDto dto2 = null;
		try {
			dto2 = service.save(dto);
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		map.put("dto", dto2);
		
		return map;
	}
	
	//전체 검색
	@GetMapping("")
	public Map list() {
		ArrayList<NoticeDto> list = null;
		Map map = new HashMap();
		boolean flag = true;
		try {
			list = service.getAll();
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		map.put("list", list);
		
		return map;
	}
	
	//카테고리로 검색
	@GetMapping("/{category}")
	public Map getByCategory(@PathVariable("category") int category) {
		ArrayList<NoticeDto> flist = service.getByCategory(category);
		Map map = new HashMap<>();
		map.put("Notice", flist);
		
		return map;
	}
	
	//수정
	@PatchMapping()
	public Map edit(NoticeDto dto) {
		service.save(dto);
		Map map = new HashMap<>();
		map.put("Notice", dto);
		
		return map;
	}
	
	//삭제
	@DeleteMapping("/{num}")
	public Map del(@PathVariable("num") int noticenum) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			service.delNotice(noticenum);
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		
		return map;
	}
	
}