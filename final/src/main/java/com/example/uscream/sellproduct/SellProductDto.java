package com.example.uscream.sellproduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellProductDto {
	
	private int sellproductnum;
	private String sellproductname;
	private int sellproductprice;
}
