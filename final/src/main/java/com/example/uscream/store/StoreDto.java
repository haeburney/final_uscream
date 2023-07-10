package com.example.uscream.store;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
	private String storeid ;
	private String storename;
	private String pwd;
	private String managername;
	private int accounttype;		//본사: 1, 점주:2
	private String storeimg;		//이미지 경로
	private float x; 
	private float y;
	private MultipartFile simg;
}




