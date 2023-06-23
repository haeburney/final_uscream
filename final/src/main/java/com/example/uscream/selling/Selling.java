package com.example.uscream.selling;

import java.util.Date;

import com.example.uscream.sellproduct.SellProduct;
import com.example.uscream.store.Store;

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
public class Selling {
	 
	@Id
	@SequenceGenerator(name = "seq_selling", sequenceName = "seq_selling", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_selling") 
	private int sellingnum;
	
	@ManyToOne
	@JoinColumn(name="sellproduct", nullable=false)
	private SellProduct sellproduct;
	
	@ManyToOne
	@JoinColumn(name="storeid", nullable=false)
	private Store storeid;	
	
	private Date sellingdate;
	private int sellingcnt;
	private int sellingprice;
	
	@PrePersist	// sellingprice 자동으로 구하는 쿼리: sell product 테이블의 상품정보(판매가)를 가져와서 sellingcnt(수량)을 곱함
	public void calculateSellingPrice() {
		int sellproductprice = sellproduct.getSellproductprice();
		sellingprice = sellproductprice * sellingcnt;
	}
	

}

