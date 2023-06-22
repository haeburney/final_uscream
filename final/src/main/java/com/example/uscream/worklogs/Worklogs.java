package com.example.uscream.worklogs;

import java.util.Date;

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
public class Worklogs { // 알바 출퇴근 시간이 기록되는 곳
	@Id
	@SequenceGenerator(name="seq_worklogs", sequenceName="seq_worklogs", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_worklogs")
	private int wnum;
	
	@ManyToOne
	@JoinColumn(name="emp", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Emp emp;
	
	@ManyToOne
	@JoinColumn(name="storeid", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Store storeid;
	
	private Date wdate;
	private Date starttime;
	private Date endtime;
	private int alltime;
	private int latetime;
	
}
