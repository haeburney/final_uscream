package com.example.uscream.porder;

import java.util.Date;

import com.example.uscream.product.Product;
import com.example.uscream.store.Store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PorderDto {
	private int tempnum;
	private String ordernum;
	private Store storeid;
	private Product productnum;
	private int amount;
	private Date orderdate;
	private Date confirmdate;
	private int ordercost;
	private int confirm;
	private String store;
	private int product;
	
}
