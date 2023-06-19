package com.example.uscream.selling;

import java.util.Date;

import com.example.uscream.sellproduct.Sellproduct;
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

public class SellingDto {
	private int sellingnum;
	private Sellproduct sellproduct;
	private Store store;
	private Date sellingdate;
	private int sellingcnt;
	private int sellingprice;
}