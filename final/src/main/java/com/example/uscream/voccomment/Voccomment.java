package com.example.uscream.voccomment;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.uscream.store.Store;
import com.example.uscream.voc.Voc;

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
public class Voccomment {

	@Id
	@SequenceGenerator(name="seq_voccom", sequenceName="seq_voccom", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_voccom")
	private int voccomnum;
	
	private String storecomment;
	
	@ManyToOne
	@JoinColumn(name="vocnum", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Voc vocnum;
	
	
	private Date wdate;
	@PrePersist
	public void sysdate() {
		wdate = new Date();
	}
	
	@ManyToOne
	@JoinColumn(name="store", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Store storeid; //지점번호
}