package com.example.uscream.inventory;

import com.example.uscream.product.Product;
import com.example.uscream.store.Store;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Inventory {
	
	@Id
	@SequenceGenerator(name="seq_inventory", sequenceName = "seq_inventory", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inventory")
	private int inventorynum;
	
	@JoinColumn(name = "storeid", nullable = false)
	@ManyToOne
	private Store storeid;

	@JoinColumn(name = "productname", nullable = false)
	@ManyToOne
	private Product productname;
	
	private int amount;
	
	
}
