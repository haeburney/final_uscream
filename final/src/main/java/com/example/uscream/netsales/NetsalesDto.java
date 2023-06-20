package com.example.uscream.netsales;

import com.example.uscream.order.Order;
import com.example.uscream.monthlypay.Payroll;
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
	private Store storeid;
	private Selling sellingprice;
	private Monthlypay totalpayroll;
	private Order ordernum;

}
