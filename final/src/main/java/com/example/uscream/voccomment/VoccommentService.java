package com.example.uscream.voccomment;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.voc.Voc;

@Service
public class VoccommentService {

	@Autowired
	private VoccommentDao dao;
	
	//추가 & 수정
	public VoccommentDto save(VoccommentDto dto) {
		Voccomment voc = dao.save(new Voccomment(dto.getVoccomnum(), dto.getStorecomment(), dto.getVocnum(), dto.getWdate(), dto.getStoreid()));
		dto.setVoccomnum(voc.getVoccomnum());
		dto.setStorecomment(voc.getStorecomment());
		dto.setVocnum(voc.getVocnum());
		dto.setWdate(voc.getWdate());
		dto.setStoreid(voc.getStoreid());
		
		return dto;
	}
	
	//전체 검색
	public ArrayList<VoccommentDto> getAll() {
		ArrayList<Voccomment> list = (ArrayList<Voccomment>) dao.findAll();
		ArrayList<VoccommentDto> list2 = new ArrayList<VoccommentDto>();
		for(Voccomment voc : list) {
			list2.add(new VoccommentDto(voc.getVoccomnum(), voc.getStorecomment(), voc.getVocnum(), voc.getWdate(), voc.getStoreid()));
		}
		return list2;
	}
	
	//Id로 검색
	public VoccommentDto getById(int voccomnum) {
		Voccomment voc = dao.findById(voccomnum).orElse(null);
		VoccommentDto dto = new VoccommentDto(voc.getVoccomnum(), voc.getStorecomment(), voc.getVocnum(), voc.getWdate(), voc.getStoreid());
		
		return dto;
	}
	
	//vocnum로 검색
	public ArrayList<VoccommentDto> getByVocnum(int vocnum) {
		Voc schvoc = new Voc(vocnum, 0, "", "", 0, null, null, "");
		ArrayList<Voccomment> list = (ArrayList<Voccomment>) dao.findByVocnum(schvoc);
		ArrayList<VoccommentDto> list2 = new ArrayList<VoccommentDto>();
		for(Voccomment voc : list) {
			list2.add(new VoccommentDto(voc.getVoccomnum(), voc.getStorecomment(), voc.getVocnum(), voc.getWdate(), voc.getStoreid()));
		}
		
		return list2;
	}
	
	//삭제
	public void delVoccomment(int voccomnum) {
		dao.deleteById(voccomnum);
	}
}