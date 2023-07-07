package com.example.uscream.notice;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

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
	private String title;
	private long content; 
	private Date wdate;
	private int cnt;
	
	private String img1;
	private String img2;
	private String img3;
	
	private MultipartFile[] f = new MultipartFile[4];

}