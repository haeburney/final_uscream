package com.example.uscream.netsales;

import com.example.uscream.monthlypay.Monthlypay;
import com.example.uscream.order.Order;
import com.example.uscream.selling.Selling;
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
	@SequenceGenerator(name="seq_gen", sequenceName="seq_netsales", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_netsales")
	private int netsalesnum;
	
	@ManyToOne
	@JoinColumn(name="storeid", nullable=false)
	private Store store;
	
	@ManyToOne
	@JoinColumn(name="selling", nullable=false)
	private Selling selling;
	
	@ManyToOne
	@JoinColumn(name="monthlypay", nullable=false)
	private Monthlypay monthlypay;
	
	@ManyToOne
	@JoinColumn(name="order", nullable=false)
	private Order order;

}