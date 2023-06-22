package com.example.uscream.schedule;

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

import com.example.uscream.basicschedule.BasicscheduleDto;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/schedule")
public class ScheduleController {
	@Autowired
	ScheduleService service;

	// 스케줄 추가
	@PostMapping("")
	public Map addSchedule(ScheduleDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			service.save(dto);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("dto", dto);
		map.put("flag", flag);
		return map;
	}

	// 지점별로 스케줄 가져오기
	@GetMapping("/storeid/{storeid}")
	public Map getByStoreid(@PathVariable("storeid") String storeid) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<ScheduleDto> list = new ArrayList<ScheduleDto>();

		try {
			list = service.getByStoreid(storeid);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("flag", flag);
		return map;
	}

	// pk로 가져오기
	@GetMapping("/{snum}")
	public Map getBySnum(@PathVariable("snum") int snum) {
		Map map = new HashMap();
		boolean flag = true;
		ScheduleDto dto = new ScheduleDto();

		try {
			dto = service.getBySnum(snum);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("dto", dto);
		map.put("flag", flag);
		return map;
	}

	// 직원별로 가져오기
	@GetMapping("/emp/{empnum}")
	public Map getByEmpnum(@PathVariable("empnum") int empnum) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<ScheduleDto> list = new ArrayList<ScheduleDto>();

		try {
			list = service.getByEmp(empnum);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("flag", flag);
		return map;
	}

	// 기본 스케줄 수정하기 → 날짜, 시작시간, 끝나는시간 수정
	@PutMapping("")
	public Map edit(ScheduleDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		ScheduleDto originalDto = service.getBySnum(dto.getSnum());
		try {
			originalDto.setSdate(dto.getSdate());
			originalDto.setStarttime(dto.getStarttime());
			originalDto.setEndtime(dto.getEndtime());
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("dto", dto);
		map.put("flag", flag);
		return map;
	}

	// 삭제
	@DeleteMapping("/{snum}")
	public Map del(int snum) {
		Map map = new HashMap();
		boolean flag = true;

		try {
			service.delete(snum);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("flag", flag);
		return map;
	}
}
