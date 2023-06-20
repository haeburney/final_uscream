package com.example.uscream.voc;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class Voc {

	@Id
	@SequenceGenerator(name="seq_vocnum", sequenceName="seq_vocnum", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_vocnum")
	private int vocnum;
	
	private int category;
	private String title;
	private String content;
	private String storecomment; //댓글
	private int check; //본사 확인
	
	private Date wdate;
	@PrePersist
	public void sysdate() {
		wdate = new Date();
	}
	
	@ManyToOne
	@JoinColumn(name="store", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Store storeid;
	
	@Column(nullable=true)
	private String img1;
}