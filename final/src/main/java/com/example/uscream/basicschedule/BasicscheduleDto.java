package com.example.uscream.basicschedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@NoArgsConstructor
@AllArgsConstructor
public class BasicscheduleDto {
	private int bsnum;
	private Store storeid;
	private Emp emp;
	private LocalDate bsdate;
	private LocalDateTime starttime;
	private LocalDateTime endtime;
	private int status; 
}
