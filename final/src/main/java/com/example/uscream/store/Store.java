package com.example.uscream.store;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Store {
	
	@Id
	private String storeid ;
	private String storename;
	private String pwd;
	private String managername;
	private int accounttype;	//본사: 1, 점주:2
	private String storeimg;
	private float x; 
	private float y;
	
	
	
}
