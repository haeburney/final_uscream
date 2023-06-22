package com.example.uscream.netsales;

import java.util.Date;

import com.example.uscream.monthlypay.Monthlypay;
import com.example.uscream.porder.Porder;
import com.example.uscream.selling.Selling;
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
	private Date netsalesdate;
	private Store storeid;
	private int sellingprice;
	private int mpsalary;
	private int ordercost;

}
