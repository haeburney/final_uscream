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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vocs")
public class VocController {

	@Autowired
	private VocService service;
	
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
	@PostMapping()
	public Map add(VocDto dto) {
		Map map = new HashMap();
		boolean flag = true;
		VocDto dto2 = null;
		try {
			dto2 = service.save(dto);
			System.out.println("dto2:"+dto2);
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
	
	
	//카테고리로 검색
	@GetMapping("/{category}")
	public Map getByCategory(@PathVariable("category") int category) {
		ArrayList<VocDto> flist = service.getByCategory(category);
		Map map = new HashMap<>();
		map.put("Voc", flist);
		
		return map;
	}
	
	
	//수정
	@PutMapping("/{vocnum}")
	public Map edit(@PathVariable("vocnum") int vocnum, VocDto dto) {
		VocDto v = service.getById(vocnum);
		v.setCategory(dto.getCategory());
		v.setTitle(dto.getTitle());
		v.setContent(dto.getContent());
		VocDto v2 = service.save(v);
		Map map = new HashMap();
		map.put("Voc", v2);
		
		return map;
	}
	
	
	//삭제
	@DeleteMapping("/{vocnum}")
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