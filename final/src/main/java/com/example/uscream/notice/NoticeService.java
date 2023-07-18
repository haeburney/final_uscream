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
		Notice entity = dao.save(new Notice(dto.getNoticenum(), dto.getTitle(), dto.getContent(), dto.getWdate(), dto.getCnt(), dto.getImg1(), dto.getImg2(), dto.getImg3()));
		//int category = parseInt.Integer(dto.getCategory());
		dto.setNoticenum(entity.getNoticenum());
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
			list2.add(new NoticeDto(n.getNoticenum(), n.getTitle(), n.getContent(), n.getWdate(), n.getCnt(), n.getImg1(), n.getImg2(), n.getImg3(), null));
		}
		return list2;
	}
	
	
	//아이디로 검색
	public NoticeDto getById(int noticenum) {
		Notice n = dao.findById(noticenum).orElse(null);
		NoticeDto dto = new NoticeDto(n.getNoticenum(), n.getTitle(), n.getContent(), n.getWdate(), n.getCnt(), n.getImg1(), n.getImg2(), n.getImg3(), null);
		
		return dto; 
	}
	
	
	//제목으로 검색
	public ArrayList<NoticeDto> getByTitle(String title){
		ArrayList<Notice> list = (ArrayList<Notice>) dao.findByTitleContaining(title);
		ArrayList<NoticeDto> list2 = new ArrayList<NoticeDto>();
		
		for(Notice n : list) {
			list2.add(new NoticeDto(n.getNoticenum(), n.getTitle(), n.getContent(), n.getWdate(), n.getCnt(), n.getImg1(), n.getImg2(), n.getImg3(), null));
		}
		return list2;
	}
	
	//리스트 3개만 
   public ArrayList<NoticeDto> getByThree(){
	      ArrayList<Notice> list = (ArrayList<Notice>) dao.findAllByOrderByNoticenumDesc();
	      ArrayList<NoticeDto> list2 = new ArrayList<NoticeDto>();
	      
	      for(int i=0; i<3;i++) {
	          Notice n = list.get(i);
	          list2.add(new NoticeDto(n.getNoticenum(), n.getTitle(), n.getContent(), n.getWdate(), n.getCnt(), n.getImg1(), n.getImg2(), n.getImg3(), null));
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
	
	public void upCnt(int noticenum) {
		dao.upCnt(noticenum);
		
	}
}