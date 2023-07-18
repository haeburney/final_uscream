package com.example.uscream.voc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.uscream.notice.NoticeDto;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vocs")
public class VocController {

	@Autowired
	private VocService service;
	
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	@PostMapping("")
	public Map add(VocTempDto dto) {
	    Map map = new HashMap<>();
	    boolean flag = true;
	    VocDto dto2 = service.save(dto);
	    
	    if(dto2 != null) {
	    	flag = true;
	    	
	    	map.put("dto2", dto2);
	    }
	    map.put("flag", flag);	
	    
	    return map;
	}
	
	
	//전체 검색
	@GetMapping("")
	public Map list() {
		ArrayList<VocDto> list = null;
		Map map = new HashMap();
		boolean flag = true;
		try {
			list = service.getAll();
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		map.put("list", list);
		
		return map;
	}
	
	// 3개만 
	@GetMapping("/three")
	public Map listThree() {
		ArrayList<VocDto> list = null;
		Map map = new HashMap();
		boolean flag = true;
		try {
			list = service.getThree();
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		map.put("list", list);
		
		return map;
	}
	
	// 3개만 + 지점별
	@GetMapping("/three/{storeid}")
	public Map listThreeStoreid(@PathVariable("storeid") String storeid) {
		ArrayList<VocDto> list = null;
		Map map = new HashMap();
		boolean flag = true;
		try {
			list = service.getThreeAndStoreid(storeid);
			System.out.println(list);
		} catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		map.put("flag", flag);
		map.put("list", list);
		System.out.println(map);
		
		return map;
	}
	
	//아이디로 검색
	@GetMapping("/schid/{vocnum}")
	public Map getById(@PathVariable("vocnum") int vocnum) {
		VocDto dto = service.getById(vocnum);
		Map map = new HashMap<>();
		map.put("voc", dto);
		
		return map;
	}
		
	
	//카테고리로 검색
	@GetMapping("/schctg/{category}")
	public Map getByCategory(@PathVariable("category") int category) {
		ArrayList<VocDto> flist = service.getByCategory(category);
		Map map = new HashMap<>();
		map.put("list", flist);
		
		return map;
	}
	
	
//	//수정
//	@PutMapping("/edit/{vocnum}")
//	public Map edit(@PathVariable("vocnum") int vocnum, VocDto dto) {
//		VocDto v = service.getById(vocnum);
//		v.setCategory(dto.getCategory());
//		v.setTitle(dto.getTitle());
//		v.setContent(dto.getContent());
//		VocDto v2 = service.save(v);
//		Map map = new HashMap();
//		map.put("voc", v2);
//		
//		return map;
//	}
//	
//	@PutMapping("/checkedit/{vocnum}")
//	public Map checkedit(@PathVariable("vocnum") int vocnum, VocDto dto) {
//		VocDto v = service.getById(vocnum);
//		v.setVoccheck(1);
//		VocDto v2 = service.save(v);
//		Map map = new HashMap();
//		map.put("voc", v2);
//		
//		return map;
//	}
	
	//삭제
	@DeleteMapping("/del/{vocnum}")
	public Map del(@PathVariable("vocnum") int vocnum) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			service.delVoc(vocnum);
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		
		return map;
	}
	
	//이미지
	@GetMapping("/imgs/{vocnum}/{idx}")  
	public ResponseEntity<byte[]> read_img(@PathVariable("vocnum") int vocnum, @PathVariable("idx") int idx) {
		String fname = "";
		VocDto dto = service.getById(vocnum);  
		switch(idx) {
		case 1:
			fname = dto.getImg1(); 
			break;
		default:
			return null;
		}
		System.out.println(fname);
		
		File f = new File(fname);
		HttpHeaders header = new HttpHeaders(); 
		ResponseEntity<byte[]> result = null; 
		try {
			header.add("Content-Type", Files.probeContentType(f.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f),
					header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}