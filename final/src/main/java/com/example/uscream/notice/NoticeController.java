package com.example.uscream.notice;

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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/notices")
public class NoticeController {

	@Autowired
	private NoticeService service;
	
	@Value("${spring.servlet.multipart.location}")
	private String path;  
	
	@PostMapping("")
	public Map add(NoticeDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		NoticeDto dto2 = null;
		try {
			dto2 = service.save(dto);
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		map.put("dto", dto2);
		
		return map;
	}
	
	
	//전체 검색
	@GetMapping("")
	public Map list() {
		ArrayList<NoticeDto> list = null;
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
	
	
	//아이디로 검색
	@GetMapping("/schid/{noticenum}")
	public Map getById(@PathVariable("noticenum") int noticenum) {
		NoticeDto dto = service.getById(noticenum);
		Map map = new HashMap<>();
		map.put("notice", dto);
		
		return map;
	}
	
	
	//제목으로 검색
	@GetMapping("/schtit/{title}")
	public Map getByTitleLike(@PathVariable("title") String title) {
		ArrayList<NoticeDto> list = service.getByTitle(title);
		Map map = new HashMap<>();
		map.put("list", list);
		
		return map;
	}
	
	
	//수정
	@PutMapping("/edit/{noticenum}")
	public Map edit(@PathVariable("noticenum") int noticenum, NoticeDto dto) {
	    NoticeDto n = service.getById(noticenum);
	    n.setTitle(dto.getTitle());
	    n.setContent(dto.getContent());
	    
	    NoticeDto n2 = service.save(n);
	    Map map = new HashMap<>();
	    map.put("Notice", n2);
	      
	    return map;
	}


	//삭제
	@DeleteMapping("/del/{noticenum}")
	public Map del(@PathVariable("noticenum") int noticenum) {
		Map map = new HashMap();
		boolean flag = true;
		try {
			service.delNotice(noticenum);
		} catch(Exception e) {
			flag = false;
		}
		map.put("flag", flag);
		
		return map;
	}
	
	
	//이미지
	@GetMapping("/imgs/{noticenum}/{idx}")  
	public ResponseEntity<byte[]> read_img(@PathVariable("noticenum") int noticenum, @PathVariable("idx") int idx) {
		String fname = "";
		NoticeDto dto = service.getById(noticenum);  
		switch(idx) {
		case 1:
			fname = dto.getImg1(); 
			break;
		case 2:
			fname = dto.getImg2();
			break;
		case 3:
			fname = dto.getImg3();
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