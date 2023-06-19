package com.example.uscream.sellproduct;

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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SellProduct {
	
	@Id
	@SequenceGenerator(name="seq_sellproduct",sequenceName = "seq_sellproduct",allocationSize = 1)
	@GeneratedValue(strategy =GenerationType.SEQUENCE, generator ="seq_sellproduct")
	private int sellproductnum;
	private String sellproductname;
	private int sellproductprice;
}
