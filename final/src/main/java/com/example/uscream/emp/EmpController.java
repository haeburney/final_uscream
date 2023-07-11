package com.example.uscream.emp;

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

import com.example.uscream.store.Store;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/emp")
public class EmpController {
	@Autowired
	EmpService service;
	
	// 지점별 직원 리스트
	// 완! 
	@GetMapping("/storeid/{storeid}")
	public Map getByStoreid(@PathVariable("storeid") String storeid) {
		Map map = new HashMap<>();
		boolean flag = true;
		ArrayList<EmpDto> list = new ArrayList<>();
		
		try {
			list = service.getByStoreId(storeid);
		} catch ( Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("flag", flag);
		
		return map;
	}
	
	// 직원 아이디로 검색
	// 완!
	@GetMapping("/{empnum}")
	public Map getByEmpnum(@PathVariable("empnum") int empnum) {
		Map map = new HashMap<>();
		boolean flag = true;
		EmpDto dto = new EmpDto();
		try {
			dto = service.getById(empnum);
		} catch(Exception e) {
			flag = false;
			e.printStackTrace();
		}
		//System.out.println("dto :"+dto);
		map.put("dto", dto);
		map.put("flag", flag);
		
		return map;
	}
	
	// 지점 아이디로 검색 + 이름순
	@GetMapping("/name/storeid/{storeid}")
	public Map getByStoreidAndName(@PathVariable("storeid") String storeid) {
		Map map = new HashMap<>();
		boolean flag = true;
		ArrayList<EmpDto> list = new ArrayList<>();
		
		try {
			list = service.getByStoreIdAndName(storeid);
		} catch ( Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("flag", flag);
		
		return map;
	}
	
	// 직원 등록 
	// 완!
	@PostMapping("")
	public Map addEmp(EmpDto dto) {
		Map map = new HashMap<>();
		boolean flag = true;
		EmpDto empDto = new EmpDto();
		
		try {
			empDto = service.save(dto);
		} catch(Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("dto", empDto);
		map.put("flag", flag);
		return map;
	}
	
	// 직원 수정 : 직원이름, 등급, 입사날짜, 컬러 수정 
	// 완!
	@PutMapping("")
	public Map edit(EmpDto dto) {
		Map map = new HashMap<>();
		boolean flag = true;
		EmpDto empDto = new EmpDto();
		
		try {
			int empnum = service.getById(dto.getEmpnum()).getEmpnum();
			empDto = service.getById(empnum);
			empDto.setColor(dto.getColor());
			empDto.setEmpname(dto.getEmpname());
			empDto.setGrade(dto.getGrade());
			empDto.setJoindate(dto.getJoindate());
			service.save(empDto);
		} catch (Exception e) {
			flag = false;
		}
		
		map.put("dto", empDto);
		map.put("flag", flag);
		return map;
	}
	
	// 직원 삭제
	// 완!
	@DeleteMapping("/{empnum}")
	public Map del(@PathVariable("empnum") int empnum) {
		Map map = new HashMap<>();
		boolean flag = true;
		
		try {
			service.delete(empnum);
		} catch(Exception e) {
			flag = false;
			e.printStackTrace();
		}
		
		map.put("flag", flag);
		return map;
	}
}
