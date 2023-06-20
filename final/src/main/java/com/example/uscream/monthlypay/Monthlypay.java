package com.example.uscream.monthlypay;

import java.util.Date;

import com.example.uscream.emp.Emp;

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
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Monthlypay {
	@Id
	@SequenceGenerator(name="seq_monthlypay", sequenceName="seq_monthlypay", allocationSize =1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_monthlypay")
	private int mpnum;
	
	private Emp emp;
	private String storeId;
	private Date mpmonth;
	private int mpsalary;
	
}
