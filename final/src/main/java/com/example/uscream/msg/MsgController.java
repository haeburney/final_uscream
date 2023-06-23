package com.example.uscream.msg;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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

import com.example.uscream.store.StoreDto;
import com.example.uscream.store.StoreService;

@RestController 
@CrossOrigin(origins = "*")
@RequestMapping("/msg")
public class MsgController {
	
	private MsgService service;
	private StoreService storeservice;
	
	@Value("${spring.servlet.multipart.location}")
	private String path; 
	
	@Autowired
	public MsgController(MsgService service, StoreService storeservice) {
		super();
		this.service = service;
		this.storeservice = storeservice;
	}
	
	//메일 작성
	@PostMapping("")
	public Map sendMsg(MsgDto dto) {
		File dir = new File(path+"/"+"msgfile");
		dir.mkdir();										
		
		MultipartFile f = dto.getMfile();					
		
		if(f != null) {
		String fname = f.getOriginalFilename();				
		String newpath = path+"/"+"msgfile/"+fname; 		  
		File uploadfile = new File(newpath);		
		
		try {
			f.transferTo(uploadfile);					
			dto.setMsgfile(newpath);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		service.save(dto);
		
		
		Map map = new HashMap();
		map.put("dto", dto);
		return map;
	}
	
	// 답장 페이지에 정보 주기 
	@PostMapping("/reply")
	public Map sendReply(MsgDto redto) {
		//num, title, receiver, sender, date, content, file ......... 
		MsgDto dto = new MsgDto();
		
		dto.setSender(redto.getReceiver());
		dto.setReceiver(redto.getSender());
		dto.setReply(redto.getMsgnum());
		
	
		
		Map map = new HashMap();
		map.put("dto", dto);
		
		return map;
	}
	
	// 파일 다운로드 ??
	@GetMapping("/down/{fname}")
	public ResponseEntity<byte[]> down(@PathVariable("fname") String fname) {
		File f = new File(path+"/"+"msgfile/"+fname);		
		HttpHeaders header = new HttpHeaders();
		ResponseEntity<byte[]> result = null;
		try {		
			header.add("Content-Type", Files.probeContentType(f.toPath()));// 마임타입
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + URLEncoder.encode(fname, "UTF-8") + "\"");
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(f), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}			
		return result;
	}	
		
	//인덱스 페이지 , 수신자로 검색
	@GetMapping("/{storeid}")
	public Map getIndex(@PathVariable("storeid") String id) {
		
		StoreDto store = storeservice.getById(id);
		long CountByReceiver = service.countAllReceiver(store.getStoreid());
		long CountByReceiverAndRead = service.countAllReadReceiver(store.getStoreid());
		ArrayList<MsgDto> msglist = service.getByReceiver(store);
		Map map = new HashMap();
		map.put("CountByReceiver",CountByReceiver);
		map.put("CountByReceiverAndRead",CountByReceiverAndRead);
		map.put("msglist",msglist);
		
		return map;
	}
	
	// 디테일 정보
	@GetMapping("/detail/{msgnum}")
	public Map getDetail(@PathVariable("msgnum") int msgnum) {
		
		MsgDto msgdto = service.getMsg(msgnum);
		Map map = new HashMap();
		map.put("msgdto",msgdto);
		return map;
	}
	
	// 즐겨찾기
	@PatchMapping("/mark/check/{msgnum}")
	public void changeMark(@PathVariable("msgnum") int msgnum) {
		service.changeMark(msgnum);
	}
	
	// 임시 보관
	@PatchMapping("/temp/check/{msgnum}")
	public void changeTemp(@PathVariable("msgnum") int msgnum) {
		service.changeTempcheck(msgnum);
	}
	
	// 읽음 처리
	@PatchMapping("/read/check/{msgnum}")
	public void changeRead(@PathVariable("msgnum") int num) {
		service.changeReadcheck(num);
	}
	
	// 휴지통으로 보내기
	@PatchMapping("/del/check/{msgnum}")
	public void changeDelcheck(@PathVariable("msgnum") int num) {
		service.changeDelcheck(num);
	}
	
	// 완전 삭제 
	@DeleteMapping("{msgnum}")
	public void deleteReal(@PathVariable("msgnum") int num) {
		ArrayList<Integer> intlist = new ArrayList<Integer>();
	
		for(int i=0;i<=intlist.size();i++) {
			
			service.delete(num);
		}
	}
	
	//즐겨찾기 인덱스 페이지
	@GetMapping("/mark/{storeid}")
	public Map getMarkPage(@PathVariable("storeid") String storeid) {
		
		StoreDto store = storeservice.getById(storeid);
		ArrayList<MsgDto> list = service.getByReceiver(store);
		ArrayList<MsgDto> msglist = new ArrayList<MsgDto>();
		
		//전체 조회를 하고 안에서 mark 표시가 된 객체만 리스트에 담는다.
		for(MsgDto markMsg : list) {
			if(markMsg.isMark()==true) {
					msglist.add(new MsgDto(markMsg.getMsgnum(),markMsg.getSender(),markMsg.getReceiver(),markMsg.getTitle(),markMsg.getMsgdate(),
							markMsg.getContent(),markMsg.getMsgfile(),null,markMsg.getReply(),markMsg.isMark(),markMsg.isTempcheck(),
							markMsg.isReadcheck(),markMsg.isDelcheck()));
			}
		}
		// 카운트 
		long CountByMark = service.countByMark(store.getStoreid());
		long CountByMarkRead = service.countByMarkRead(store.getStoreid());
		
		Map map = new HashMap();
		map.put("msglist", msglist);
		map.put("CountByMark", CountByMark);
		map.put("CountByMarkRead", CountByMarkRead);
		return map;
	}	
	
	
	// 보낸 메세지 인덱스 페이지
		@GetMapping("/sender/{storeid}")
		public Map getSendPage(@PathVariable("storeid") String storeid) {
			
			StoreDto store = storeservice.getById(storeid);
			ArrayList<MsgDto> msglist = service.getBySender(store);
			// 카운트 
			long CountBySender = service.countBySender(store.getStoreid());
			long CountBySenderRead = service.countBySenderRead(store.getStoreid());
			
			Map map = new HashMap();
			map.put("msglist", msglist);
			map.put("CountBySender", CountBySender);
			map.put("CountBySenderRead", CountBySenderRead);
			return map;
		}	
		
	//	임시보관 인덱스 페이지 , 발신자로 조회 
	@GetMapping("/temp/{sender}")
	public Map getTempPage(@PathVariable("sender") String storeid) {
			
		StoreDto store = storeservice.getById(storeid);
		ArrayList<MsgDto> list = service.getBySender(store);
		ArrayList<MsgDto> msglist = new ArrayList<MsgDto>();
		
		//	보낸 사람으로 전체 조회를 하고 안에서 temp 표시가 된 객체만 리스트에 담는다.
		for(MsgDto markMsg : list) {
			if(markMsg.isTempcheck()==true) {
					msglist.add(new MsgDto(markMsg.getMsgnum(),markMsg.getSender(),markMsg.getReceiver(),markMsg.getTitle(),markMsg.getMsgdate(),
							markMsg.getContent(),markMsg.getMsgfile(),null,markMsg.getReply(),markMsg.isMark(),markMsg.isTempcheck(),
							markMsg.isReadcheck(),markMsg.isDelcheck()));
			}
		}

		long CountBySenderTemp = service.countBySenderTemp(store.getStoreid());
		long CountBySenderTempRead = service.countBySenderTempRead(store.getStoreid());
		
		Map map = new HashMap();
		map.put("CountBySenderTemp", CountBySenderTemp);
		map.put("CountBySenderTempRead", CountBySenderTempRead);
		map.put("msglist", msglist);
			
		return map;
	}
	
	//	휴지통 인덱스 페이지 , 발신자로 조회 
	@GetMapping("/del/{sender}")
	public Map getDelPage(@PathVariable("sender") String storeid) {
			
		StoreDto store = storeservice.getById(storeid);
		ArrayList<MsgDto> list = service.getByReceiver(store);
		ArrayList<MsgDto> msglist = new ArrayList<MsgDto>();
		
		//	보낸 사람으로 전체 조회를 하고 안에서 del 표시가 된 객체만 리스트에 담는다.
		for(MsgDto markMsg : list) {
			if(markMsg.isDelcheck()==true) {
					msglist.add(new MsgDto(markMsg.getMsgnum(),markMsg.getSender(),markMsg.getReceiver(),markMsg.getTitle(),markMsg.getMsgdate(),
							markMsg.getContent(),markMsg.getMsgfile(),null,markMsg.getReply(),markMsg.isMark(),markMsg.isTempcheck(),
							markMsg.isReadcheck(),markMsg.isDelcheck()));
			}
		}
		
		long CountByDel = service.countByDel(store.getStoreid());
		long CountByDelRead = service.countByDelRead(store.getStoreid());
		
		Map map = new HashMap();
		map.put("CountByDel", CountByDel);
		map.put("CountByDelRead", CountByDelRead);
		map.put("msglist", msglist);
			
		return map;
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
