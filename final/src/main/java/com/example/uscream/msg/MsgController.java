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
		System.out.println("메일작성시작");
		Map map = new HashMap();
		File dir = new File(path+"/"+"msgfile");   
		dir.mkdir();										
		
		MultipartFile f = dto.getMfile();	// 멀티파트파일 감자
		
		if(f != null) {
		String fname = f.getOriginalFilename();			//감자 
		
		 		  
		String newpath = path+"/"+"msgfile/"+fname; //실제 파일 경로  
		
		File uploadfile = new File(newpath);		
		
		try {
			f.transferTo(uploadfile);	// img 폴더에 파일이 들어간다. 				
			dto.setMsgfile(fname);
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
		
		System.out.println("메일작성끝 + " +dto.getReceiver());
		if(dto.getReceiver()==null) {
		
			
			map.put("dto",null);
		}else {
			service.save(dto);
			map.put("dto", dto);
			
		}
		
		
		
		
		
		return map;
	}
	
	// 임시보관 메일 작성
		@PostMapping("/temp")
		public Map sendTempMsg(MsgDto dto) {
			File dir = new File(path+"/"+"msgfile");   
			dir.mkdir();										
			
			MultipartFile f = dto.getMfile();	// 멀티파트파일 감자
			
			if(f != null) {
			String fname = f.getOriginalFilename();			//감자 
			
			 		  
			String newpath = path+"/"+"msgfile/"+fname; //실제 파일 경로  
			
			File uploadfile = new File(newpath);		
			
			try {
				f.transferTo(uploadfile);	// img 폴더에 파일이 들어간다. 				
				dto.setMsgfile(fname);
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
			
			service.saveTemp(dto);
			
			
			Map map = new HashMap();
			map.put("dto", dto);
			return map;
		}
		
	
	@GetMapping("/search/{receiver}")
	public Map getReceiver(@PathVariable("receiver") String receiver) {
		Map map = new HashMap();
		
		StoreDto dto = storeservice.getById(receiver);
		if(dto==null) {
			map.put("dto", null);
		}else {
			map.put("dto", dto);
		
		}
		
		
		
		
		return map;
	}
		
		
	// 답장 페이지에 정보 주기 
	@GetMapping("/reply/{num}")
	public Map sendReply(@PathVariable("num") int num) {

		
		MsgDto dto = service.getMsg(num);
		MsgDto msgdto = new MsgDto();
		
		msgdto.setSender(dto.getReceiver());
		msgdto.setReceiver(dto.getSender());
		msgdto.setReply(dto.getMsgnum());
		msgdto.setTitle(dto.getTitle());
		
		
		
		Map map = new HashMap();
		map.put("msgdto", msgdto);
		
		return map;
	}
	
	// 파일 다운로드 
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
		Map countmap = service.countAllReceiverMsg(store.getStoreid());
	
		if( countmap == null) {
			return null;
		}
		
		Long countByReadReceiveMsg= (Long)countmap.get("countByReadReceiveMsg");;
		if(countByReadReceiveMsg == null) {
			countByReadReceiveMsg=	0L;
		}
		
		Long countAllByReadReceiveMsg=(Long)countmap.get("countAllByReadReceiveMsg");
		if(countAllByReadReceiveMsg==null) {
			countAllByReadReceiveMsg=0L;
		}
	
		
		ArrayList<MsgDto> msglist = service.selectAllReceiveMsg(store.getStoreid());
		
		
		Map map = new HashMap();
		map.put("countByReadReceiveMsg",countByReadReceiveMsg);
		map.put("countAllByReadReceiveMsg",countAllByReadReceiveMsg);
		map.put("msglist",msglist);
		
		return map;
	}
	
	//받은 메세지에서 보낸 사람으로 검색 
	@GetMapping("/receivemsg/{sender}/{receiver}")
	public Map getMsgBySender(@PathVariable("sender") String sender,@PathVariable("receiver") String receiver) {
		
		ArrayList<MsgDto> msglist = service.selectBySender(sender,receiver);
		Map map = new HashMap();
		map.put("msglist", msglist);
		
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
	// 디테일용 읽음 처리
	@PatchMapping("/read/detail/check/{msgnum}")
	public void fixRead(@PathVariable("msgnum") int num) {
		service.fixReadcheck(num);
	}
	
	// 휴지통으로 보내기
	@PatchMapping("/del/check/{msgnum}")
	public void changeDelcheck(@PathVariable("msgnum") int num) {
		service.changeDelcheck(num);
	}
	
	
	
	// 즐겨찾기 인덱스 페이지
	@GetMapping("/mark/{storeid}")
	public Map getMarkPage(@PathVariable("storeid") String storeid) {
		
		StoreDto store = storeservice.getById(storeid);
		
		ArrayList<MsgDto> msglist = service.selectAllMark(store.getStoreid());
		
		//전체 수
		Long CountByMark =(Long)service.countSideBarMarkMsg(store.getStoreid());
		
		if(CountByMark ==null) {
			CountByMark=0L;
		}
		Long CountByMarkAndRead =(Long)service.countByReadAndMarkRead(store.getStoreid());
		
		if(CountByMarkAndRead ==null) {
			CountByMarkAndRead=0L;
		}
	
		
		Map map = new HashMap();
		map.put("msglist", msglist);
		map.put("CountByMarkAndRead", CountByMarkAndRead);
		map.put("CountByMark", CountByMark);
		return map;
	}	
	
	//사이드 바 
	@GetMapping("/sidebar/{storeid}")
	public Map getSideBar(@PathVariable("storeid") String storeid) {
		StoreDto store = storeservice.getById(storeid);
		
		
		
		Long countSideBarReadMsg = (Long)service.countSideBarReadMsg(store.getStoreid());;
		
		if(countSideBarReadMsg == null) {
			
			countSideBarReadMsg=0L;
		}
		
		
		Long countSideBarMarkMsg =  (Long)service.countSideBarMarkMsg(store.getStoreid());
		if(countSideBarMarkMsg == null) {
			countSideBarMarkMsg=0L;
		}
	
		
		Map map = new HashMap();
		
		map.put("countSideBarReadMsg", countSideBarReadMsg);
		map.put("countSideBarMarkMsg", countSideBarMarkMsg);
		
		
		return map;
	}
	
	// 보낸 메세지 인덱스 페이지
		@GetMapping("/sender/{storeid}")
		public Map getSendPage(@PathVariable("storeid") String storeid) {
			
			StoreDto store = storeservice.getById(storeid);
			ArrayList<MsgDto> msglist = service.selectAllSendMsg(store.getStoreid());
			
			Map countmap = service.countAllSendMsg(store.getStoreid());
			
			Long countByReadSendMsg= (Long)countmap.get("countByReadSendMsg"); ;
			if(countByReadSendMsg ==null) {
				countByReadSendMsg =0L;					
			}
			
			Long countAllByReadSendMsg= (Long)countmap.get("countAllByReadSendMsg"); ;
			if(countAllByReadSendMsg ==null) {
				countAllByReadSendMsg =0L;	
				
			}
			

			
			Map map = new HashMap();
			map.put("msglist", msglist);
			map.put("countByReadSendMsg", countByReadSendMsg);
			map.put("countAllByReadSendMsg", countAllByReadSendMsg);
			return map;
		}	
		
		//보낸 메세지 받은 사람으로 검색
		@GetMapping("/sendermsg/{sender}/{receiver}")
		public Map getMsgByReceiver(@PathVariable("sender") String sender,@PathVariable("receiver") String receiver) {
		ArrayList<MsgDto> msglist = service.selectByReceiver(sender,receiver);
			Map map = new HashMap();
			map.put("msglist", msglist);
			
			return map;
		}
		
		
	//	임시보관 인덱스 페이지
	@GetMapping("/temp/{sender}")
	public Map getTempPage(@PathVariable("sender") String storeid) {
			
		StoreDto store = storeservice.getById(storeid);
		ArrayList<MsgDto> msglist = service.selectAllTempMsg(store.getStoreid());
		
		Map countmap = service.countAllByTempMsg(store.getStoreid());

		Long countByReadTempMsg= (Long)countmap.get("countByReadTempMsg"); ;
		if(countByReadTempMsg ==null) {
			countByReadTempMsg =0L;	
			
		}
				
		Long countAllByTempMsg= 	(Long)countmap.get("countAllByTempMsg");
		if(countAllByTempMsg ==null) {
			countAllByTempMsg = 0L;
			
		}
		
		
		Map map = new HashMap();
		map.put("countByReadTempMsg", countByReadTempMsg);
		map.put("countAllByTempMsg", countAllByTempMsg);
		map.put("msglist", msglist);
			
		return map;
	}
	
	//임시보관에서 받을 사람으로 검색
	@GetMapping("/sendertempmsg/{sender}/{receiver}")
	public Map getMsgByReceiverAndTemp(@PathVariable("sender") String sender,@PathVariable("receiver") String receiver) {
	ArrayList<MsgDto> msglist = service.selectByReceiverTemp(sender,receiver);
		Map map = new HashMap();
		map.put("msglist", msglist);
		
		return map;
	}
	
	//	휴지통 인덱스 페이지  (( 객체 확인 작업이 필요 )) 
	@GetMapping("/del/{sender}/{receiver}")
	public Map getDelPage(@PathVariable("sender") String sender, @PathVariable("receiver") String receiver ) {
		
		StoreDto store1 = storeservice.getById(sender);
		StoreDto store2 = storeservice.getById(receiver);
		boolean flag = false;
		
		if(store1 !=null) {
			flag=true;
		}
		
		if(store2 !=null) {
			flag=true;
		}
		
		ArrayList<MsgDto> msglist = service.selectAllDelMsg(sender,receiver);

		Map countmap = service.countAllByDelMsg(sender,receiver);
		
	
		Long countAllByDelAndReadMsg= (Long)countmap.get("countAllByDelAndReadMsg");
		if(countAllByDelAndReadMsg == null ) {
			
			countAllByDelAndReadMsg=0L;	
		}
		
		Long countAllByDelMsg= (Long)countmap.get("countAllByDelMsg");
		if(countAllByDelMsg == null ) {
			countAllByDelMsg=0L; 
		}
		
		
		
		Map map = new HashMap();
		map.put("countAllByDelAndReadMsg", countAllByDelAndReadMsg);
		map.put("countAllByDelMsg", countAllByDelMsg);
		map.put("msglist", msglist);
		map.put("flag", flag);
			
		return map;
	}
	
	
	//완전 삭제
	@DeleteMapping("/del/{msgnum}")
	public void deleteReal(@PathVariable("msgnum") int num) {
		
		service.deleteReal(num);
	}
	
	//전체 삭제
	@DeleteMapping("/del/all/{name}")
	public Map deleteAllMsg(@PathVariable("name") String name) {
		StoreDto store = storeservice.getById(name);
		boolean flag = false; 
		Map map = new HashMap();
		if(store != null) {
			flag = true;
			int number = service.deleteAll(name);
			map.put("flag",flag);
			map.put("number", number);
		}
		
		map.put("flag", flag);
		
		
		return map;
	}
	
	

}
