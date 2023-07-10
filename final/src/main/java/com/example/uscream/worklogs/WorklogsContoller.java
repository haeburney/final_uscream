package com.example.uscream.worklogs;

import java.time.Duration;
import java.time.LocalDateTime;
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

import com.example.uscream.schedule.ScheduleDto;
import com.example.uscream.schedule.ScheduleService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/worklogs")
public class WorklogsContoller {
	@Autowired
	WorklogsService wlService;

	@Autowired
	ScheduleService sdService;

	// 시간 계산 함수
	public int[] calculateDuration(LocalDateTime scStarTime, LocalDateTime scEndTime, LocalDateTime dtoStartTime,
			LocalDateTime dtoEndTime) {
		int arr[] = new int[3];

		Duration lateDuration = Duration.between(scStarTime, dtoStartTime);
		Duration workDuration = Duration.between(scStarTime, scEndTime);
		Duration endDuration = Duration.between(dtoEndTime, scEndTime);
		// (workDuration) 일 한 시간은 스케줄에 등록된 일 한 시간을 구했다.
		// 나중에 worklogs를 비교해서 늦었으면 빼주고, 초과근무를 했으면 더해지는 방식으로 구했다.

		arr[0] = (int) lateDuration.getSeconds() / 60; // 0: 늦은 시간 (출근 시간)
		arr[1] = (int) endDuration.getSeconds() / 60; // 1: 끝난 시간
		arr[2] = (int) workDuration.getSeconds() / 60; // 2: 일 한 시간

		return arr;
	}

	// 근무 일지 추가
	// 완!
	@PostMapping("")
	public Map addWorklogs(WorklogsDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<ScheduleDto> scList = sdService.getByEmpAndDate(dto.getEmp().getEmpnum(), dto.getWdate());

		try {
			// 시간 계산 하는 걸 함수로 따로 빼서 결과를 받아왔다.
			if (scList.size() > 0) { // 리스트가 1개 이상일 때만 계산을 할 수 있게
				int arr[] = calculateDuration(scList.get(0).getStarttime(), scList.get(0).getEndtime(),
						dto.getStarttime(), dto.getEndtime());

				if (arr.length > 2) {
					
					dto.setLatetime(arr[0]);
					// -로 뜨지만 이렇게 만들어줬다.
					// 왜냐하면 초과 근무 시간도 만들어줄려고
					
					/*
					if (arr[0] > 0) { // 만약 늦었으면 늦은 시간을 넣어주고
						dto.setLatetime(arr[0]);
					} else { // 늦지 않으면 음수로(ex.-15) 뜨기 때문에 0으로 넣어줬다.
						dto.setLatetime(0);
					}
					*/

					dto.setAlltime(arr[2] - dto.getLatetime() + arr[1]); // 총 일 한 시간을 계산해서 넣어주기
					dto.setStoreid(dto.getEmp().getStoreid()); // emp를 참조하여 업데이트 해주기

					wlService.save(dto);
				} else {
					System.out.println("WorklogsController POST를 보세요 뭔가 이상하게 흘러가고 있을거에요..");
				}
			} else {
				System.out.println("이때 등록한 스케줄 기록이 없는뎁쇼?");
			}

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("dto", dto);
		map.put("flag", flag);
		return map;
	}

	// 근무 일지 수정 → 일자랑 근무 시간만 수정
	// 완!!
	@PutMapping("")
	public Map edit(WorklogsDto dto) {
		Map map = new HashMap();
		boolean flag = true;

		ArrayList<ScheduleDto> scList = sdService.getByEmpAndDate(dto.getEmp().getEmpnum(), dto.getWdate());
		WorklogsDto originDto = wlService.getByWnum(dto.getWnum());

		try {
			originDto.setWdate(dto.getWdate());
			originDto.setStarttime(dto.getStarttime());
			originDto.setEndtime(dto.getEndtime());

			int arr[] = calculateDuration(scList.get(0).getStarttime(), scList.get(0).getEndtime(), dto.getStarttime(),
					dto.getEndtime());

			if (arr.length > 2) {
				originDto.setAlltime(arr[2] - dto.getLatetime() + arr[1]);
				originDto.setLatetime(dto.getLatetime());

				if (arr[0] > 0) { // 만약 늦었으면 늦은 시간을 넣어주고
					dto.setLatetime(arr[0]);
				} else { // 늦지 않으면 음수로(ex.-15) 뜨기 때문에 0으로 넣어줬다.
					dto.setLatetime(0);
				}
			} else {
				System.out.println("WorklogsController PUT를 보세요 뭔가 이상하게 흘러가고 있을거에요..");
			}

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("dto", originDto);
		map.put("flag", flag);
		return map;
	}

	// 하나 가져오기
	// 완!!
	@GetMapping("/{wnum}")
	public Map getByWnum(@PathVariable("wnum") int wnum) {
		Map map = new HashMap();
		boolean flag = true;
		WorklogsDto dto = new WorklogsDto();

		try {
			dto = wlService.getByWnum(wnum);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("dto", dto);
		map.put("flag", flag);
		return map;
	}
	
	// 직원 아이디로 가져오기
	@GetMapping("empnum/{empnum}")
	public Map getByEmpnum(@PathVariable("empnum") int empnum) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<WorklogsDto> list = new ArrayList<WorklogsDto>();
		try {
			list = wlService.getByEmp(empnum);
		}catch(Exception e) {
			flag = false;
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("flag", flag);
		return map;
	}

	// 지점별로 가져오기
	// 아마 완!!
	@GetMapping("/storeid/{storeid}")
	public Map getMyStoreid(@PathVariable("storeid") String storeid) {
		Map map = new HashMap();
		boolean flag = true;
		ArrayList<WorklogsDto> list = new ArrayList<WorklogsDto>();

		try {
			list = wlService.getByStoreid(storeid);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("flag", flag);
		return map;
	}

	// 삭제
	// 완!!
	@DeleteMapping("/{wnum}")
	public Map del(@PathVariable("wnum") int wnum) {
		Map map = new HashMap();
		boolean flag = true;

		try {
			wlService.delete(wnum);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("flag", flag);
		return map;
	}
}
