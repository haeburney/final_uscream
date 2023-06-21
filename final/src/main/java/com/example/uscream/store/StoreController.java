package com.example.uscream.store;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/store")
public class StoreController {

	@Autowired
	public StoreService service;
	
	@Value("spring.servlet.multipart.location")
	private String path; 
	
	//1.등록
	@PostMapping("")
	public Map add(StoreDto dto) {
		StoreDto s = service.save(dto);
		Map map = new HashMap() {};
		map.put("dto", s);
		return map;
	}
	
	
	//2.수정
	
	
	
	//3. 전체조회
	@GetMapping("")
	public Map getAll() {
		ArrayList<StoreDto> list = service.getAll();
		Map map = new HashMap();
		map.put("storelist", list);
		return map;
	}
	
	//3.1 지점하나만 조회
		@GetMapping("{storeid}")
		public Map getByid(@PathVariable("storeid") String storeid) {
			StoreDto dto = service.getById(storeid);
			Map map = new HashMap();
			map.put("dto", dto);
			return map;
		}
	
	//4. 삭제
	@DeleteMapping("{storeid}")
	public Map delStore(@PathVariable("storeid") String storeid) {
		boolean flag = true;
		try {
		service.deleteStore(storeid);
		}catch(Exception e){
			flag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		return map;
	}

	
	
	//이미지 읽어오기
	@GetMapping("/read_img")
	public ResponseEntity<byte[]> read_img(String fname){
		File f = new File(path+fname);
		HttpHeaders header = new HttpHeaders();
		ResponseEntity<byte[]> result = null;
		try {
			header.add("Content-Type", Files.probeContentType(f.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f),
					header, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
