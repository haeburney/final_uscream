package com.example.uscream.schedule;

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
public class ScheduleDto {
	private int snum;
	private Emp emp;
	private Store storeid;
	private LocalDate sdate;
	private LocalDateTime  starttime;
	private LocalDateTime  endtime;
}
