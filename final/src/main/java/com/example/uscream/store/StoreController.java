package com.example.uscream.store;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
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
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin("*")
@RequestMapping("/store")
public class StoreController {

	@Autowired
	public StoreService service;
	
	@Value("${spring.servlet.multipart.location}")
	private String path; 
	
	//1.등록
	@PostMapping("")
	public Map add(StoreDto dto) {
		StoreDto s = service.save(dto);
		File dir = new File(path+"/"+"store");
					
		dir.mkdir();							// 폴더 생성  name= path + store
		String img = dto.getStoreimg();		 
		MultipartFile F = dto.getSimg();
		
		if (F != null) {
			String fname = F.getOriginalFilename();				//원본파일명 -> 클라이언트 컴퓨터에 저장된 찐이름 
			System.out.println("3 "+fname);
			
			String newpa = dir.getAbsolutePath();				// 절대경로
			
			String newpath = dir.getAbsolutePath()+"/"+fname;
			
			File newfile = new File(newpath);
			try {
				F.transferTo(newfile);				// 실제 업로드 
				img= newpath;						// 파일 경로를 담는다  == ㅊc: ........ .png
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			img = "noimg";
		}
		
		dto.setStoreimg(img);		// 데이터를 storeimg 쏜다. 
		
		service.save(dto);
		Map map = new HashMap<>();
		map.put("store", s);
		return map;
	}
	
	
	//2.수정
//	@PostMapping("")
//	public Map edit(StoreDto dto) {
//		service.save(dto);
//		Map map = new HashMap<>();
//		map.put("store", dto);
//		return map;
//	}
	
	
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
		
	//3.2 지점하나만 조회(지점명으로 검색)
		
	
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

	
	//이미지 불러오기
	@GetMapping("/img/{storeid}")
	public ResponseEntity<byte[]> read_img(@PathVariable("storeid") String storeid){
		String fname = "";
		StoreDto dto = service.getById(storeid);
		fname=dto.getStoreimg();
		ResponseEntity<byte[]> result = null; //선언
		try {
			fname = URLDecoder.decode(fname, "utf-8");
			File f = new File(fname);
			HttpHeaders header = new HttpHeaders(); //HttpHeaders 객체 생성
			header.add("Content-Type", Files.probeContentType(f.toPath()));//응답 데이터의 종류를 설정
			//응답 객체 생성
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f),
					header, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
