package com.example.uscream.monthlypay;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/monthlypay")
public class MonthlypayController {

//	@Autowired
//	MonthlypayService mpService;
//
//	@Autowired
//	EmpService eService;
//	
//	@Autowired
//	WorklogsService wlService;
//
//	@Scheduled(cron = "0 0 0 1 * ?") // 매달 1일 00:00:00에 실행
//	public void myScheduledFunction() {
//		// 원하는 동작을 수행하는 함수 코드 작성
//
//	}
//
//	// 테스트함수 테이블에 값 넣어보려고~
//	@GetMapping("/test")
//	public Map getMonthlypay() {
//		Map map = new HashMap();
//		boolean flag = true;
//		MonthlypayDto mpDto = new MonthlypayDto();
//		
//		LocalDate today = LocalDate.now();
//		int year = today.getYear();
//		int month = today.getMonthValue();
//
//		System.out.println("year :" + year);
//		System.out.println("month :" + month);
//
//		ArrayList<Object[]> monthList = wlService.getByYearAndMonth(year, month);
//		
//		for(Object[] row:monthList) {
//			int sumAlltime = (int) row[0];
//			int emp = (int) row[1];
//			EmpDto newEmp = eService.getById(emp);
//			
//			mpDto.setEmp(new Emp(emp, null, "", null, null, ""));
//			mpDto.setStoreid(newEmp.getStoreid());
//			
//			
//			int pay = (int)(sumAlltime/60*newEmp.getGrade().getSalary());
//			
//			mpDto.setMpsalary(pay);
//			
//			DateTimeFormatter formatter;
//			LocalDate date;
//			if(month<10) {
//				formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//				date = LocalDate.parse(String.valueOf(year) + "/0" + String.valueOf(month) + "/01", formatter);
//				
//			} else {
//				formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//				date = LocalDate.parse(String.valueOf(year) + "/" + String.valueOf(month) + "/01", formatter);
//			}
//			
//			
//			//LocalDate date = LocalDate.parse(year + "/" + month, formatter);
//			System.out.println(date);
//			System.out.println(date.format(formatter));
//			
//			mpDto.setMpmonth(date);
//			
//			System.out.println(sumAlltime + " / " + emp);
//			System.out.println(pay);
//			System.out.println(mpDto);
//			
//			
//		}
//		
//		
//		map.put("flag", flag);
//		return map;
//	}
}
