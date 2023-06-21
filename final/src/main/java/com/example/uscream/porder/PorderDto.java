package com.example.uscream.porder;

import java.util.Date;

import com.example.uscream.product.Product;
import com.example.uscream.store.Store;

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
public class PorderDto {
	private int tempnum;
	private String ordernum;
	private Store storeid;
	private Product product;
	private int amount;
	private Date orderdate;
	private Date confirmdate;
	private int ordercost;
	private boolean confirm;
}
