package com.example.uscream.voc;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.store.Store;
import com.example.uscream.voccomment.Voccomment;
import com.example.uscream.voccomment.VoccommentDto;

@Service
public class VocService {

	@Autowired
	private VocDao dao;
	
	//추가 & 수정
	public VocDto save(VocDto dto) {
		Voc v = dao.save(new Voc(dto.getVocnum(), dto.getCategory(), dto.getTitle(), dto.getContent(), dto.getVoccheck(), dto.getWdate(), dto.getStoreid(), dto.getImg1()));
		dto.setVocnum(v.getVocnum());
		dto.setCategory(v.getCategory());
		dto.setTitle(v.getTitle());
		dto.setContent(v.getContent());
		dto.setVoccheck(v.getVoccheck());
		dto.setWdate(v.getWdate());
		dto.setStoreid(v.getStoreid());
		dto.setImg1(v.getImg1());
		
		return dto;
	}
	
	//전체 검색
	public ArrayList<VocDto> getAll() {
		ArrayList<Voc> list = (ArrayList<Voc>) dao.findAll();
		ArrayList<VocDto> list2 = new ArrayList<VocDto>();
		for(Voc v : list) {
			list2.add(new VocDto(v.getVocnum(), v.getCategory(), v.getTitle(), v.getContent(), v.getVoccheck(), v.getWdate(), v.getStoreid(), v.getImg1(), null));
		}
		return list2;
	}
	
	// 3개만 받아오기 + 전체
	public ArrayList<VocDto> getThree(){
		ArrayList<Voc> list = (ArrayList<Voc>) dao.findAllByOrderByWdateDesc();
		ArrayList<VocDto> list2 = new ArrayList<VocDto>();
		for(int i=0;i<3;i++) {
			Voc v = list.get(i);
			list2.add(new VocDto(v.getVocnum(), v.getCategory(), v.getTitle(), v.getContent(), v.getVoccheck(), v.getWdate(), v.getStoreid(), v.getImg1(), null));
			
		}
		return list2;
	}
	
	// 3개만 받아오기 + 지점별
	public ArrayList<VocDto> getThreeAndStoreid(String storeid){
		Store s = new Store(storeid, "", "", "", 0, "", 0, 0);
		ArrayList<Voc> list = (ArrayList<Voc>) dao.findByStoreidOrderByWdateDesc(s);
		ArrayList<VocDto> list2 = new ArrayList<VocDto>();
		for(int i=0;i<3;i++) {
			Voc v = list.get(i);
			list2.add(new VocDto(v.getVocnum(), v.getCategory(), v.getTitle(), v.getContent(), v.getVoccheck(), v.getWdate(), v.getStoreid(), v.getImg1(), null));
			
		}
		return list2;
	}
	
	//Id로 검색
	public VocDto getById(int vocnum) {
		Voc v = dao.findById(vocnum).orElse(null);
		VocDto dto = new VocDto(v.getVocnum(), v.getCategory(), v.getTitle(), v.getContent(), v.getVoccheck(), v.getWdate(), v.getStoreid(), v.getImg1(), null);
		
		return dto;
	}
	
	//카테고리로 검색
	public ArrayList<VocDto> getByCategory(int category) {
		ArrayList<Voc> list = dao.findByCategory(category);
		ArrayList<VocDto> list2 = new ArrayList<VocDto>();
		
		for(Voc v : list) {
			list2.add(new VocDto(v.getVocnum(), v.getCategory(), v.getTitle(), v.getContent(), v.getVoccheck(), v.getWdate(), v.getStoreid(), v.getImg1(), null));
		}
		return list2;
	}
	
	//storeid
	public ArrayList<VocDto> getByStoreid(String storeid) {
		Store schsid = new Store(storeid, "", "", "", 0, "", 0, 0);
		ArrayList<Voc> list = dao.findByStoreid(schsid);
		ArrayList<VocDto> list2 = new ArrayList<VocDto>();
		for (Voc v : list) {
			list2.add(new VocDto(v.getVocnum(), v.getCategory(), v.getTitle(), v.getContent(), v.getVoccheck(), v.getWdate(), v.getStoreid(), v.getImg1(), null));
		}
		return list2;
	}
	
	//삭제
	public void delVoc(int vocnum) {
		dao.deleteById(vocnum);
	}
	
}