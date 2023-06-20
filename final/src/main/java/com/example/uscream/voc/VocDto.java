package com.example.uscream.voc;

import java.util.Date;

import com.example.uscream.store.Store;

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
public class VocDto {

	private int vocnum;
	private int category;
	private String title;
	private String content;
	private String storecomment;
	private int check;
	private Date wdate;
	private Store storeid;
	
	private String img1;
}