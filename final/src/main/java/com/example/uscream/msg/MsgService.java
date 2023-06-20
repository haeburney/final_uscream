package com.example.uscream.msg;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.account.Account;
import com.example.uscream.store.Store;



@Service
public class MsgService {

	private MsgDao dao;
	
	@Autowired
	public MsgService(MsgDao dao) {
		super();
		this.dao = dao;
	}
	
	// 생성 및 수정 
	public MsgDto save(MsgDto msgdto) {
		
		Msg msg = dao.save(new Msg(msgdto.getMsgnum(),msgdto.getSender(),msgdto.getReceiver(),msgdto.getTitle(),msgdto.getDate(),
				msgdto.getContent(),msgdto.getFile(),msgdto.getImg(),msgdto.getReply(),msgdto.isMark(),msgdto.isTempcheck(),
				msgdto.isReadcheck(),msgdto.isDelcheck())); 
		
		return new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getDate(),
				msg.getContent(),msg.getFile(),null,msg.getImg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
				msg.isReadcheck(),msg.isDelcheck());
	}
	
	//메일 하나 조회
	public MsgDto getMsg(int num) {
		
		Msg msg = dao.findById(num).orElse(null);
		
		if(msg==null) {
			return null;						
		}
		
		return new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getDate(),
				msg.getContent(),msg.getFile(),null,msg.getImg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
				msg.isReadcheck(),msg.isDelcheck());
	}
	
	// 발신자로 검색 
	public ArrayList<MsgDto> getByName(String name) {
		
		ArrayList<Msg> msglist = (ArrayList<Msg>)dao.findBySenderLike("%"+name+"%");
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
		
		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getDate(),
					msg.getContent(),msg.getFile(),null,msg.getImg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
					msg.isReadcheck(),msg.isDelcheck()));
		}
		return dtolist;
	}
	
	// 수신자로 검색
		public ArrayList<MsgDto> countByReceiver(String receiver) {
			ArrayList<Msg> msglist = dao.findByReceiver(receiver);
			ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
			
			for (Msg msg : msglist) {
				dtolist.add(new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getDate(),
						msg.getContent(),msg.getFile(),null,msg.getImg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
						msg.isReadcheck(),msg.isDelcheck()));
			}
			return dtolist;
		}
		
	// 제목으로 검색 
	public ArrayList<MsgDto> getByTitle(String title) {

		ArrayList<Msg> msglist = (ArrayList<Msg>)dao.findByTitleLike(title);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
			
		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getDate(),
					msg.getContent(),msg.getFile(),null,msg.getImg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
					msg.isReadcheck(),msg.isDelcheck()));
		}
		return dtolist;
		
	}
	
	//전체 메일 조회 (날짜순으로)
	public ArrayList<MsgDto> getAllByDate(){
		
		ArrayList<Msg> msglist = (ArrayList<Msg>)dao.findAllOrderByDateAsc();
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
		
		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getDate(),
					msg.getContent(),msg.getFile(),null,msg.getImg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
					msg.isReadcheck(),msg.isDelcheck()));
		}
		return dtolist;
	}
	
	//임시보관 기능
	public void changeTempcheck(int num) {
		
		Msg msg = dao.findById(num).orElse(null);
		
		
		if(msg.isTempcheck()==true) {
			msg.setTempcheck(false);		// 기본
		}else {
			msg.setTempcheck(true);			// 임시보관
		}
		
	}
	
	// 읽음 기능
	public void changeReadcheck(int num) {
	
		Msg msg = dao.findById(num).orElse(null);
		
		
		if(msg.isReadcheck()==true) {
			msg.setReadcheck(false);		// 읽지않음
		}else {
			msg.setReadcheck(true);			// 읽음
		}
		
	}
	
	// 휴지통 기능 
	public void changeDelcheck(int num) {
			
		Msg msg = dao.findById(num).orElse(null);
		
		
		if(msg.isDelcheck()==true) {
			msg.setDelcheck(false);			// 기본
		}else {
			msg.setDelcheck(true);			// 휴지통
		}
		
	}	
	
	
	
	
	// 글 번호로 완전 삭제
	public void delete(int num) {
		dao.deleteById(num);
	}
	
	// 수신자로 완전 삭제
	public void deleteAllByReceiver(Store receiver) {
		dao.deleteByReceiver(receiver);
	}
	
	
	//=========================================================
	// 전체 메일 수
	public long countAllBySenderAndReceiver(Store store){
		return dao.countAll(store);
	}
	
	// 전체 메일 중에서 읽지 않은 메일 수
	public long countAllBySenderAndReceiverAndRead(Store store){
		return dao.countAllRead(store);
	}
	
	//=========================================================
	
	
	//=========================================================
	// 즐찾에 있는 메일 전체 수
	public long countBy(Store store){
		return dao.countByMark(store);
	}
	
	// 즐찾에 있는 메일 중에서 읽지 않은 메일 수
	public long countByDelRead(Store store){
		return dao.countByDelRead(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================
	
	
	
	//=========================================================
	// 받은 메일함에 있는 메일 전체 수
	public long countAll(Store store){
		return dao.countAll(store);
	}
	
	// 받은 메일함에 있는 메일 중에서 읽지 않은 메일 수
	public long countAllRead(Store store){
		return dao.countAllRead(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================
	
	//=========================================================
	// 보낸 메일에 있는 메일 전체 수
	public long countByVender(Store store){
		return dao.countByVender(store);
	}
	
	// 보낸 메일에 있는 메일 중에서 읽지 않은 메일 수
	public long countByVenderTemp(Store store){
		return dao.countByVenderTemp(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================

	
	//=========================================================
	// 임시 보관에 있는 메일 전체 수
	public long countByD(Store store){
		return dao.countByTemp(store);
	}
	
	// 임시 보관에 있는 메일 중에서 읽지 않은 메일 수
	public long countByDelRe(Store store){
		return dao.countByTempRead(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================
	
	
	//=========================================================
	// 휴지통에 있는 메일 전체 수
	public long countByDel(Store store){
		return dao.countByDelcheck(store);
	}
	
	// 휴지통에 있는 메일 중에서 읽지 않은 메일 수
	public long countBybyDelReads(Store store){
		return dao.countByDelRead(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================
	
	
	
	
	
	 
	
	
	
	
}
