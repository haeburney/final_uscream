package com.example.uscream.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

	@Autowired
	private NoticeDao dao;
	
	//save()
	public NoticeDto save(NoticeDto dto) {
		Notice entity = dao.save(new Notice(dto.getNoticenum(), dto.getCategory(), dto.getTitle(), dto.getContent(), dto.getWdate(), dto.getImg1(), dto.getImg2(), dto.getImg3()));
		dto.setNoticenum(entity.getNoticenum());
		dto.setCategory(entity.getCategory());
		dto.setTitle(entity.getTitle());
		dto.setContent(entity.getContent());
		dto.setWdate(entity.getWdate());
		dto.setImg1(entity.getImg1());
		dto.setImg2(entity.getImg2());
		dto.setImg3(entity.getImg3());
		
		return dto;
	}
	
	//get()
}