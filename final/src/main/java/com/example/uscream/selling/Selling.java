

package com.example.uscream.selling;

import java.util.Date;

import com.example.uscream.sellproduct.Sellproduct;
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
	
	/*
	 판매내역 Selling 
	 -(시퀀스) 넘버(pk) 
	 -판매상품 종류(fk) 
	 -지점 아이디(fk) => 지점 테이블로 변경
	 -판매 날짜 
	 -판매 수량 
	 -결제 금액
	 */
	
	@Id
	@SequenceGenerator(name="seq_gen", sequenceName="seq_selling", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_selling")
	private int sellingnum;
	
	@JoinColumn(name="sellproduct", nullable=false)
	private Sellproduct sellproduct;
	
	@ManyToOne
	@JoinColumn(name="store", nullable=false)
	private Store store;	// 매출 top3 집계 시 지점명도 필요해서 store 테이블 전체를 가져와야할 듯
	
	private Date sellingdate;
	private int sellingcnt;
	private int sellingprice;
	
	@PrePersist	// sellingprice 자동으로 구하는 쿼리: sell product 테이블의 상품정보(판매가)를 가져와서 sellingcnt(수량)을 곱함
	public void calculateSellingPrice() {
		int sellproductprice = getSellproductPriceByNum(sellproductnum);
		sellingprice = sellproductprice * sellingcnt;
	}
	

}

