package com.example.uscream.monthlypay;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uscream.emp.Emp;
import com.example.uscream.emp.EmpDto;
import com.example.uscream.emp.EmpService;
import com.example.uscream.worklogs.WorklogsService;

@RestController
@CrossOrigin(origins = "*")
@EnableScheduling
@RequestMapping("/monthlypay")
public class MonthlypayController {

	@Autowired
	MonthlypayService mpService;

	@Autowired
	EmpService eService;

	@Autowired
	WorklogsService wlService;

	// group by emp & 년 & 월별로 합계 계산하기
	public boolean sumF(int year, int month, String storeid) {

		ArrayList<Object[]> monthList = new ArrayList<Object[]>();

		if (storeid.equals("0")) {				// @Scheduled 이용 
			System.out.println("storeid = 0");
			monthList = wlService.getByYearAndMonth(year, month);
		} else {
			System.out.println("storeid " + storeid);
			// 지점마다 원하는 년, 월을 넣고 싶을 때 (그냥..혹시 몰라서 넣어둔것.. 꼭필요한거 노노..)
			monthList = wlService.getByMonthAndStoreid(year, month, storeid);
		}
		System.out.println("monthList : " + monthList);
		
		MonthlypayDto mpDto = new MonthlypayDto();
		boolean flag = true;
		try {
			for (Object[] row : monthList) { // 위 리스트만큼 for문 돌리기
				int sumAlltime = (int) row[0];
				int emp = (int) row[1];
				EmpDto newEmp = eService.getById(emp);

				mpDto.setEmp(new Emp(emp, null, "", null, null, ""));
				mpDto.setStoreid(newEmp.getStoreid());
				
				float hour = (float) sumAlltime / 60;						// 지각했을 시(ex. 2.6시간)으로 계산하기 위해서
				int pay = (int) (hour * newEmp.getGrade().getSalary());		// 등급별로 시급 곱해주기 
				
//				System.out.println("hour :" + hour);
//				System.out.println("sumAlltime : " + sumAlltime);
//				System.out.println("등급별 시급 :" + newEmp.getGrade().getSalary());
//				System.out.println("sumAlltime / 60 :" + (sumAlltime / 60));
//				System.out.println("pay : " + pay);
				
				mpDto.setMpsalary(pay);

				DateTimeFormatter formatter;
				LocalDate date;

				if (month < 10) { // 만약 month 가 10 미만이면 앞에 0 붙여주기 ..
					formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					date = LocalDate.parse(String.valueOf(year) + "/0" + String.valueOf(month) + "/01", formatter);

				} else {		 // 아니라면 그냥 가기~
					formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					date = LocalDate.parse(String.valueOf(year) + "/" + String.valueOf(month) + "/01", formatter);
				}

				mpDto.setMpmonth(date); // 년월등록
				mpService.save(mpDto);
				
				//System.out.println(mpDto);

			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		return flag;
	}

	// 매달 월급 계산해주기
	@Scheduled(cron = "0 0 0 1 * ?") 		// 매달 1일 00:00:00에 실행
	public Map myScheduledFunction() {
		Map map = new HashMap();
		MonthlypayDto mpDto = new MonthlypayDto();

		LocalDate today = LocalDate.now();
		int year = today.getYear(); 		// 해당 년도 가져오기
		int month = today.getMonthValue(); // 해당 월 가져오기

		if (month == 1) {
			month = 12;
			year -= 1;
		} else {
			month -=1;
		}

		boolean result = sumF(year, month, "0");

		//System.out.println("MonthlyPay 업데이트 성공? :" + result);
		map.put("flag", result);
		return map;
	}

	// 데이터 나중에 계산하고 싶을 수 있으니까 일단 넣어놓기
	// 자동 계산 말고 수동 계산 하는 곳 (클릭해서 계산할 수 있게?)
	// 완!! 
	@GetMapping("/sum/{storeid}/{year}/{month}")
	public Map getSumMonth(@PathVariable("storeid") String storeid, @PathVariable("year") int year,
			@PathVariable("month") int month) {
		Map map = new HashMap();
		String msg="계산 완료";
		System.out.println("year : " + year + " / month : " + month);
		ArrayList<MonthlypayDto> list = mpService.getByStoreAndMonth(storeid, year, month);
		
		boolean result = false; 
		if(list.isEmpty()) {			// 계산이 안 된 월이 있을 때만 계산해주기 
			 result = sumF(year, month, storeid);
		} else {
			msg = "이미 계산이 완료됐습니다.";
		}


		map.put("flag", result);
		map.put("mpMsg", msg);
		return map;
	}

	// list 스토어 & 년 & 월
	// 아마 완!
	@GetMapping("/{storeid}/{year}/{month}")
	public Map getStoreAndMonth(@PathVariable("storeid") String storeid, @PathVariable("year") int year,
			@PathVariable("month") int month) {
		Map map = new HashMap();
		ArrayList<MonthlypayDto> list = new ArrayList<MonthlypayDto>();
		boolean flag = true;

		try {
			list = mpService.getByStoreAndMonth(storeid, year, month);
			if(!list.isEmpty()) {
				System.out.println("list.get(0).getEmp() :" + list.get(0).getEmp());
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("flag", flag);
		return map;
	}

	// list 스토어 & 년별로 보기
	// 완!!
	@GetMapping("/{storeid}/{year}")
	public Map getStoreAndYear(@PathVariable("storeid") String storeid, @PathVariable("year") int year) {
		Map map = new HashMap();
		ArrayList<MonthlypayDto> list = new ArrayList<MonthlypayDto>();
		boolean flag = true;
		try {
			list = mpService.getByStoreAndYear(storeid, year);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("flag", flag);
		return map;
	}

	// list all
	// 완!!
	@GetMapping("")
	public Map getAll() {
		Map map = new HashMap();
		ArrayList<MonthlypayDto> list = new ArrayList<MonthlypayDto>();
		boolean flag = true;

		try {
			list = mpService.getByAll();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("flag", flag);
		return map;
	}

	// 삭제하기
	// 완!!
	@DeleteMapping("/{mpnum}")
	public Map del(@PathVariable("mpnum") int mpnum) {
		Map map = new HashMap();
		boolean flag = true;

		try {
			mpService.delete(mpnum);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		map.put("flag", flag);
		return map;
	}

}
