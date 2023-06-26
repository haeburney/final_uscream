package com.example.uscream.porder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.uscream.product.Product;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Porder {
	

	@Id
	@SequenceGenerator(name="seq_order",sequenceName = "seq_order",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_order")
	private int tempnum;
	
	private String ordernum;
	
	
	@ManyToOne
	@JoinColumn(name="store", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Store storeid;
	
	@ManyToOne
	@JoinColumn(name="product", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product productnum;
	
	private int amount;
	private Date orderdate;
	@Column(nullable = true)
	private Date confirmdate;
	private int ordercost;
	private boolean checkconfirm;
	
	
	@PrePersist
	public void sysdate() {
		orderdate = new Date();
		confirmdate = null;
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyymmdd");
		Date sysdate = new Date();
		String date = simpleDate.format(sysdate);
		ordernum = date +"#"+storeid.getStoreid();
	}
	
	
	
	
}
