package com.example.uscream.msg;

import java.io.File;  
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
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Msg {
	
	@Id
	@SequenceGenerator(name="seq_msg", sequenceName="seq_msg", allocationSize=1)	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_msg")	
	private int msgnum;
	
	@ManyToOne
	@JoinColumn(name="sender", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Store sender;			// 발신자
	
	@ManyToOne
	@JoinColumn(name="receiver", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Store receiver;		// 수신자
	
	private String title;
	private Date msgdate;
	private String content;
	
	@Column(nullable = true)
	private String msgfile;
	
	@Column(columnDefinition = "number default 0")
	private int reply;			// 답장
	
	@Column(columnDefinition = "number default 0")
	private boolean mark;			// 즐겨찾기 			0=기본 1=즐찾
	@Column(columnDefinition = "number default 0")
	private boolean tempcheck;		// 임시보관 			0=기본 1=임시보관
	@Column(columnDefinition = "number default 0")
	private boolean readcheck;		// 읽었는지 안읽었는지	0=기본 1=읽음
	@Column(columnDefinition = "number default 0")
	private boolean delcheck;		//	휴지통으로 			0=기본 1=휴지통
	
	
	@PrePersist
	public void updateDate() {
		msgdate = new Date();
	}
	
}
