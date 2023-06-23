package com.example.uscream.netsales;

import java.time.LocalDate;

import com.example.uscream.store.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Netsales {
	@Id
	@SequenceGenerator(name="seq_netsales", sequenceName="seq_netsales", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_netsales")
	private int netsalesnum;
	
	@Column(nullable=false)
	private LocalDate netsalesdate;
	
	@ManyToOne
	@JoinColumn(name="storeid", nullable=false)
	private Store storeid;
	
	private int msellingprice;
	private int mpsalary;
	private int mordercost;
	private int mnetsales;
	
//	@ManyToOne
//	@JoinColumn(name="sellingprice", nullable=false)
//	private Selling sellingprice;

//	@ManyToOne
//	@JoinColumn(name="mpsalary", nullable=false)
//	private Monthlypay mpsalary;
	
//	@ManyToOne
//	@JoinColumn(name="ordernum", nullable=false)
//	private Porder ordernum;

	
	@PrePersist	// netsales 자동 계산: '월별 매출 - (월 급여 + 월 발주금액)'
	public void calculateNetsales() {
		mnetsales = msellingprice - (mpsalary + mordercost);
	}

}