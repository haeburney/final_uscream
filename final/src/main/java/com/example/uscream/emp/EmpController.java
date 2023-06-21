package com.example.uscream.emp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.uscream.store.Store;

@Controller
@RequestMapping("/emp")
public class EmpController {
//	@Autowired
//	EmpService service;
//	
//	// 지점별 직원 리스트 
//	@GetMapping("/storeid/{storeid}")
//	public Map getByStoreid(@PathVariable("storeid") Store storeid) {
//		Map map = new HashMap<>();
//		boolean flag = true;
//		ArrayList<EmpDto> list = new ArrayList<>();
//		
//		try {
//			list = service.getByStoreId(storeid);
//		} catch ( Exception e) {
//			flag = false;
//			e.printStackTrace();
//		}
//		map.put("list", list);
//		map.put("flag", flag);
//		
//		return map;
//	}
//	
//	// 직원 아이디로 검색
//	@GetMapping("/{empnum}")
//	public Map getByEmpnum(@PathVariable("empnum") int empnum) {
//		Map map = new HashMap<>();
//		boolean flag = true;
//		EmpDto dto = new EmpDto();
//		try {
//			dto = service.getById(empnum);
//		} catch(Exception e) {
//			flag = false;
//			e.printStackTrace();
//		}
//		map.put("dto", dto);
//		map.put("flag", flag);
//		
//		return map;
//	}
//	
//	// 직원 등록 
//	@PostMapping("")
//	public Map addEmp(EmpDto dto) {
//		Map map = new HashMap<>();
//		boolean flag = true;
//		EmpDto empDto = new EmpDto();
//		try {
//			empDto = service.save(dto);
//		} catch(Exception e) {
//			flag = false;
//			e.printStackTrace();
//		}
//		map.put("dto", empDto);
//		map.put("flag", flag);
//		return map;
//	}
//	
//	// 직원 수정 : 직원이름, 등급, 입사날짜, 컬러 수정 
//	@PutMapping("")
//	public Map edit(EmpDto dto) {
//		Map map = new HashMap<>();
//		boolean flag = true;
//		int empnum = 0;
//		
//		try {
//			empnum = service.getById(dto.getEmpnum()).getEmpnum();
//			
//		} catch (Exception e) {
//			flag = false;
//			e.printStackTrace();
//		}
//		
//		EmpDto empDto = new EmpDto();
//		
//		try {
//			empDto = service.getById(empnum);
//			empDto.setColor(dto.getColor());
//			empDto.setEmpname(dto.getEmpname());
//			empDto.setGrade(dto.getGrade());
//			empDto.setJoindate(dto.getJoindate());
//		} catch (Exception e) {
//			flag = false;
//		}
//		
//		map.put("dto", empDto);
//		map.put("flag", flag);
//		return map;
//	}
//	
//	// 직원 삭제
//	@DeleteMapping("{/empnum}")
//	public Map del(@PathVariable("id") int empnum) {
//		Map map = new HashMap<>();
//		boolean flag = true;
//		
//		try {
//			service.delete(empnum);
//		} catch(Exception e) {
//			flag = false;
//			e.printStackTrace();
//		}
//		
//		map.put("flag", flag);
//		return map;
//	}
}
