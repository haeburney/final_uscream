package com.example.uscream.netsales;

import java.util.Date;

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
public class Netsales {
	@Id
	@SequenceGenerator(name="seq_netsales", sequenceName="seq_netsales", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_netsales")
	private int netsalesnum;
	
	// 월이 없다..
	private Date netsalesdate;
	
	@ManyToOne
	@JoinColumn(name="storeid", nullable=false)
	private Store storeid;
	
//	@ManyToOne
//	@JoinColumn(name="sellingprice", nullable=false)
//	private Selling sellingprice;
	
	private int sellingprice;
	
//	@ManyToOne
//	@JoinColumn(name="mpsalary", nullable=false)
//	private Monthlypay mpsalary;
	
	private int mpsalary;
	
//	@ManyToOne
//	@JoinColumn(name="ordernum", nullable=false)
//	private Porder ordernum;

	private int ordercost;
	
}