package com.example.uscream.selling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Selling {
	
	@Id
	@SequenceGenerator(name="seq_selling",sequenceName = "seq_selling", allocationSize = 1)
	@GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "seq_selling")
	private int sellingnum;
	
	@ManyToOne
	@JoinColumn(name="sellproduct" ,nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private SellProduct sellproduct;
	@ManyToOne
	@JoinColumn(name="storeid" ,nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Store storeid;
	private Date sellingdate;
	private int sellingcnt;
	private int sellingprice;
	
	@SuppressWarnings("deprecation")
	@PrePersist
	public void sysdate() {
		Date nowDate = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		
		
		String x = simpleDateFormat.format(nowDate);
		try {
			sellingdate = simpleDateFormat.parse(x);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

//select * from selling where sellingdate = 2023-06-17
