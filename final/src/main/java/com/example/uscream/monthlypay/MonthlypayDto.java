package com.example.uscream.monthlypay;

import java.util.Date;

import com.example.uscream.emp.Emp;
import com.example.uscream.store.Store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MonthlypayDto {
	private int mpnum;
	private Emp emp;
	private Store storeid;
	private Date mpmonth;
	private int mpsalary;	
}
