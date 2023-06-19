package com.example.uscream.notice;

import java.util.Date;

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
public class NoticeDto {

	private int noticenum;
	private int category;
	private String title;
	private String Content;
	private Date wdate;
	
	private String img1;
	private String img2;
	private String img3;
}