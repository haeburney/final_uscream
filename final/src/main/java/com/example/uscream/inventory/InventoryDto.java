package com.example.uscream.inventory;

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
public class InventoryDto {

	private int inventorynum;
	private Store storeid;
	private Product productname;
	private int amount;
}
