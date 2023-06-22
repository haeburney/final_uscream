package com.example.uscream.voccomment;

import java.util.Date;

import com.example.uscream.store.Store;
import com.example.uscream.voc.Voc;

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
public class VoccommentDto {

	private int voccomnum;
	private String storecomment;
	private Voc vocnum;
	private Date wdate;
	private Store storeid;
}