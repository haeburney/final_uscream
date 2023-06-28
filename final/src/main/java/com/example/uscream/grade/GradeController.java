package com.example.uscream.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/grade")
public class GradeController {
	@Autowired
	private GradeService service;
	
	// 등급 셀렉 한 정보 불러오기
	@GetMapping("/{gnum}")
	public Map getSalary(@PathVariable("gnum") int gnum) {
		Map map = new HashMap();
		boolean flag = true;
		GradeDto dto = new GradeDto();
		try {
			dto = service.getSalary(gnum);
		}catch(Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("flag", flag);
		map.put("dto", dto);
		return map;
	}
	
	@GetMapping("/all")
	public Map getAll() {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<GradeDto> list = new ArrayList<>();
		try {
			list = service.getAll();
		}catch(Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("flag", flag);
		map.put("list", list);
		return map;
	}
	
	@PostMapping("")
	public Map addGrade(GradeDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		GradeDto newDto = new GradeDto();
		try {
			newDto = service.save(dto);
		} catch(Exception e) {
			flag = false;
			e.printStackTrace();
		}
		
		map.put("flag", flag);
		map.put("dto", newDto);
		return map;
	}
}
