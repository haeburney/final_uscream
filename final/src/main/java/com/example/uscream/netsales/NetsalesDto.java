package com.example.uscream.netsales;

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
	private Store storeid;
	private Selling sellingprice;
	private Monthlypay totalpayroll;
	private Porder ordernum;

}
