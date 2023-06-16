package com.example.uscream.store;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
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
	private String storebranch; 
	private float x; 
	private float y; 
	
	
	private static int num = 1;
	
	
	@PrePersist
	public void makeid(){
		storeid = storebranch+"_"+num ;
		num += num +1;
	}
	
	
}
