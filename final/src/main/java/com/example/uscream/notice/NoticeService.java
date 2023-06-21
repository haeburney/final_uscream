package com.example.uscream.notice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

	@Autowired
	private NoticeDao dao;
	
	//글 추가
	public NoticeDto save(NoticeDto dto) {
		Notice entity = dao.save(new Notice(dto.getNoticenum(), dto.getCategory(), dto.getTitle(), dto.getContent(), dto.getWdate(), dto.getCnt(), dto.getImg1(), dto.getImg2(), dto.getImg3()));
		dto.setNoticenum(entity.getNoticenum());
		dto.setCategory(entity.getCategory());
		dto.setTitle(entity.getTitle());
		dto.setContent(entity.getContent());
		dto.setWdate(entity.getWdate());
		dto.setCnt(entity.getCnt());
		dto.setImg1(entity.getImg1());
		dto.setImg2(entity.getImg2());
		dto.setImg3(entity.getImg3());
		
		return dto;
	}
	
	//글 전체 검색
	public ArrayList<NoticeDto> getAll() {
		ArrayList<Notice> list = (ArrayList<Notice>) dao.findAll();
		ArrayList<NoticeDto> list2 = new ArrayList<NoticeDto>();
		for(Notice n : list) {
			list2.add(new NoticeDto(n.getNoticenum(), n.getCategory(), n.getTitle(), n.getContent(), n.getWdate(), n.getCnt(), n.getImg1(), n.getImg2(), n.getImg3()));
		}
		return list2;
	}
	
	//아이디로 검색
	public NoticeDto getById(int noticenum) {
		Notice n = dao.findById(noticenum).orElse(null);
		NoticeDto dto = new NoticeDto(n.getNoticenum(), n.getCategory(), n.getTitle(), n.getContent(), n.getWdate(), n.getCategory(), n.getImg1(), n.getImg2(), n.getImg3());
		
		return dto; 
	}
	
	//카테고리로 검색
	public ArrayList<NoticeDto> getByCategory(int category) {
		ArrayList<Notice> list = dao.findByCategory(category);
		ArrayList<NoticeDto> list2 = new ArrayList<NoticeDto>();
		
		for(Notice n : list) {
			list2.add(new NoticeDto(n.getNoticenum(), n.getCategory(), n.getTitle(), n.getContent(), n.getWdate(), n.getCnt(), n.getImg1(), n.getImg2(), n.getImg3()));
		}
		return list2;
		 
	}
	
	//삭제
	public void delNotice(int noticenum) {
		dao.deleteById(noticenum);
	}
	
	//조회수
	public void editCnt(int noticenum) {
		dao.updateCnt(noticenum);
	}
}