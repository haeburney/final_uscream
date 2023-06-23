package com.example.uscream.netsales;

import java.time.LocalDate;

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
 
public class NetsalesDto {
	private int netsalesnum;
	private LocalDate netsalesdate;
	private Store storeid;
	private int msellingprice;
	private int mpsalary;
	private int mordercost;
	private int mnetsales;

}
