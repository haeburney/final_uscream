package com.example.uscream.notice;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Notice {

	@Id
	@SequenceGenerator(name="seq_gen", sequenceName="seq_noticenum", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_noticenum")
	private int noticenum;
	
	private int category;
	private String title;
	private String content;
	
	private Date wdate;
	@PrePersist
	public void preprocess() {
		wdate = new Date(); //현재 날짜 생성
	}
	
	@Column(nullable=true)
	private String img1;
	
	@Column(nullable=true)
	private String img2;
	
	@Column(nullable=true)
	private String img3;
}