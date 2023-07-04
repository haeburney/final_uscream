package com.example.uscream.basicschedule;

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
public class Basicschedule {
	@Id
	@SequenceGenerator(name="seq_basicschedule", sequenceName="seq_basicschedule", allocationSize =1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_basicschedule")
	private int bsnum;		// 기본 스케줄 pk num
	
	@ManyToOne
	@JoinColumn(name="storeid", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Store storeid;	// 지점
	
	@ManyToOne
	@JoinColumn(name="emp", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Emp emp;		// 직원 
	
	private LocalDate bsdate;	// 일하는 날짜
	private LocalDateTime starttime;	// 출근 시간
	private LocalDateTime endtime;	// 퇴근 시간
	
	private int status; 		// 스케줄에 추가 했는지 체크
}
