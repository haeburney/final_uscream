package com.example.uscream.emp;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.uscream.grade.Grade;
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
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Emp {
	@Id
	@SequenceGenerator(name="seq_emp", sequenceName="seq_emp", allocationSize =1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_emp")
	private int empnum; 	// 직원 번호
	
	@ManyToOne
	@JoinColumn(name="storeid", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Store storeid;	// 지점 아이디
	
	private String empname;	// 직원 이름
	private Date joindate;	// 입사 날짜
	
	@ManyToOne
	@JoinColumn(name="gnum", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Grade grade;		// 등급
	private String color;	// 컬러 
	
}
