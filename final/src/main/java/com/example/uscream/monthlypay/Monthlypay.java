package com.example.uscream.monthlypay;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.uscream.emp.Emp;
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
	
	@ManyToOne
	@JoinColumn(name="emp", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Emp emp;
	
	@ManyToOne
	@JoinColumn(name="storeid", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Store storeid;
	private LocalDate mpmonth;
	private int mpsalary;	
}
