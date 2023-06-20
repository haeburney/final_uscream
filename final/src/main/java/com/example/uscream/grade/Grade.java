package com.example.uscream.grade;

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
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
	@Id
	private int grade; // 등급 1,2,3,4  
	private int salary; // 1,1.2,1.4,1.6배 
	
}
