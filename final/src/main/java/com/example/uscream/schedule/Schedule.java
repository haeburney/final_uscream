package com.example.uscream.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
	@Id
	@SequenceGenerator(name="seq_schedule", sequenceName="seq_schedule", allocationSize =1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_schedule")
	private int snum;
	
	@ManyToOne
	@JoinColumn(name="emp", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Emp emp;
	
	@ManyToOne
	@JoinColumn(name="storeid", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Store storeid;
	
	private LocalDate sdate;
	private LocalDateTime  starttime;
	private LocalDateTime  endtime;
}
