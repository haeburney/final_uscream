package com.example.uscream.voccomment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uscream.notice.NoticeDto;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/voccomments")
public class VoccommentController {

	@Autowired
	private VoccommentService service;
	
	@PostMapping("")
	public Map add(VoccommentDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		VoccommentDto dto2 = null;
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
		ArrayList<VoccommentDto> list = null;
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
	
	
	//vocnum으로 검색
	@GetMapping("/schnum/{vocnum}")
	public Map getByVocnum(@PathVariable("vocnum") int vocnum) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<VoccommentDto> list = new ArrayList<VoccommentDto>();
		try {
			list = service.getByVocnum(vocnum);
		} catch(Exception e) {
			flag = false;
		}
		map.put("list", list);
		map.put("flag", flag);
		
		return map;
	}

	
	
	//수정
	@PutMapping("/edit/{voccomnum}")
	public Map edit(@PathVariable("voccomnum") int voccomnum, VoccommentDto dto) {
		VoccommentDto voc = service.getById(voccomnum);
		voc.setStorecomment(dto.getStorecomment());
		VoccommentDto voc2 = service.save(voc);
		Map map = new HashMap();
		map.put("voc", voc2);
		
		return map;
	}
	
	
	//삭제
	@DeleteMapping("/del/{voccomnum}")
	public Map del(@PathVariable("voccomnum") int voccomnum) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			service.delVoccomment(voccomnum);
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		
		return map;
	}
	
}