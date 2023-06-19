package com.example.uscream.product;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@Value(value = "${spring.servlet.multipart.location}")
	private String path;
	
	@PostMapping()
	public Map addProduct(ProductDto dto) {
		ProductDto d = service.save(dto);
		File dir = new File(path+"product");
		dir.mkdir();
		String img = dto.getProductimg();
		MultipartFile F = dto.getPimg();
		if (F != null) {
			String fname = F.getOriginalFilename();
			String newpath = dir.getAbsolutePath()+"/"+fname;
			File newfile = new File(newpath);
			System.out.println(newpath);
			try {
				F.transferTo(newfile);
				img= newpath;
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
		dto.setProductimg(img);
		Map map = new HashMap<>();
		map.put("product", d);
		return map;
	}
	
	@GetMapping()
	public Map getAll() {
		ArrayList<ProductDto> dlist = service.getAll();
		Map map = new HashMap<>();
		map.put("list", dlist);
		return map;
	}
	
	@GetMapping("/{productnum}")
	public Map getById(@PathVariable("productunm") int productnum) {
		ProductDto dto = service.getById(productnum);
		Map map = new HashMap<>();
		map.put("product",dto);
		return map;
		
	}
	
	
	@PatchMapping()
	public Map edit(ProductDto dto) {
		service.save(dto);
		Map map = new HashMap<>();
		map.put("product", dto);
		return map;
	}
	
	@DeleteMapping()
	public void delete(int num) {
		service.deleteProduct(num);
		
	}
	
	@GetMapping("/img/{num}")
	public ResponseEntity<byte[]> read_img(@PathVariable("num") int num){
		String fname= "";
		ProductDto dto= service.getById(num);
		fname=dto.getProductimg();
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
