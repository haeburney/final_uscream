package com.example.uscream.voc;

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
public class VocTempDto {
	private int vocnum;
	private int category;
	private String title;
	private String content;
	private int voccheck;
	private Date wdate;
	private String store;
	
	private String img1;
	private MultipartFile[] f = new MultipartFile[4];
}
