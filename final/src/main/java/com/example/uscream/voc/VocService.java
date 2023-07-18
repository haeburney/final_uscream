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
	public VocDto save(VocTempDto dto) {
		Store store = new Store(dto.getStore(), "", "", "", 0, "", 0, 0);
		Voc v = dao.save(new Voc(dto.getVocnum(), dto.getCategory(), dto.getTitle(), dto.getContent(), dto.getVoccheck(), dto.getWdate(), store, dto.getImg1()));
		VocDto d = new VocDto();
		d.setVocnum(v.getVocnum());
		d.setCategory(v.getCategory());
		d.setTitle(v.getTitle());
		d.setContent(v.getContent());
		d.setVoccheck(v.getVoccheck());
		d.setWdate(v.getWdate());
		d.setStore(store);
		d.setImg1(v.getImg1());
		
		return d;
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
		for(int i=0;i<list.size();i++) {
			Voc v = list.get(i);
			list2.add(new VocDto(v.getVocnum(), v.getCategory(), v.getTitle(), v.getContent(), v.getVoccheck(), v.getWdate(), v.getStoreid(), v.getImg1(), null));
			
		}
		return list2;
	}
	
	// 3개만 받아오기 + 지점별
	public ArrayList<VocDto> getThreeAndStoreid(String storeid){
		System.out.println(storeid);
		
		ArrayList<Voc> list = (ArrayList<Voc>) dao.findByStoreidOrderByWdateDesc(storeid);
		System.out.println(list);
		ArrayList<VocDto> list2 = new ArrayList<VocDto>();
		for(int i=0;i<list.size();i++) {
			Voc v = list.get(i);
			list2.add(new VocDto(v.getVocnum(), v.getCategory(), v.getTitle(), v.getContent(), v.getVoccheck(), v.getWdate(), v.getStoreid(), v.getImg1(), null));
			
		}
		System.out.println(list2);
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