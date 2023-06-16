package com.example.uscream.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
@Entity
public class Product {
	@Id
	@SequenceGenerator(name="seq_productnum",sequenceName = "seq_productnum",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_productnum")
	private int productnum;
	
	private String productname;
	private String productimg;
	private String productinfo;
	private int cost;
	private boolean orderble;
}
