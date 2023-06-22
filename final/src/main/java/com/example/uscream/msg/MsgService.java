package com.example.uscream.msg;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.uscream.store.Store;
import com.example.uscream.store.StoreDto;



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
		
		Msg msg = dao.save(new Msg(msgdto.getMsgnum(),msgdto.getSender(),msgdto.getReceiver(),msgdto.getTitle(),msgdto.getMsgdate(),
				msgdto.getContent(),msgdto.getMsgfile(),msgdto.getMsgimg(),msgdto.getReply(),msgdto.isMark(),msgdto.isTempcheck(),
				msgdto.isReadcheck(),msgdto.isDelcheck())); 
		
		return new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getMsgdate(),
				msg.getContent(),msg.getMsgfile(),null,msg.getMsgimg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
				msg.isReadcheck(),msg.isDelcheck());
	}
	
	//메일 하나 조회
	public MsgDto getMsg(int num) {
		
		Msg msg = dao.findById(num).orElse(null);
		System.out.println(msg);
		if(msg==null) {
			
			return null;						
		}
		
		return new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getMsgdate(),
				msg.getContent(),msg.getMsgfile(),null,msg.getMsgimg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
				msg.isReadcheck(),msg.isDelcheck());
	}
	
//	 발신자로 검색(내가 받은 메세지에서 검색) 
	public ArrayList<MsgDto> getByName(StoreDto sender) {
		
		ArrayList<Msg> msglist = (ArrayList<Msg>)dao.findBySenderLike(sender);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
		
		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getMsgdate(),
					msg.getContent(),msg.getMsgfile(),null,msg.getMsgimg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
					msg.isReadcheck(),msg.isDelcheck()));
		}
		return dtolist;
	}
	
	 //발신자로 검색 (내가 보낸)
		public ArrayList<MsgDto> getBySender(StoreDto sender) {
		
		ArrayList<Msg> msglist = (ArrayList<Msg>)dao.findBySender(sender);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
		
		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getMsgdate(),
					msg.getContent(),msg.getMsgfile(),null,msg.getMsgimg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
					msg.isReadcheck(),msg.isDelcheck()));
		}
		return dtolist;
	}
		
	// 수신자로 검색
		public ArrayList<MsgDto> getByReceiver(StoreDto receiver) {
			ArrayList<Msg> msglist = dao.findByReceiver(receiver);
			ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
			
			for (Msg msg : msglist) {
				dtolist.add(new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getMsgdate(),
						msg.getContent(),msg.getMsgfile(),null,msg.getMsgimg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
						msg.isReadcheck(),msg.isDelcheck()));
			}
			return dtolist;
		}
		
	// 제목으로 검색 
	public ArrayList<MsgDto> getByTitle(String title) {

		ArrayList<Msg> msglist = (ArrayList<Msg>)dao.findByTitleLike(title);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
			
		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(),msg.getSender(),msg.getReceiver(),msg.getTitle(),msg.getMsgdate(),
					msg.getContent(),msg.getMsgfile(),null,msg.getMsgimg(),null,msg.getReply(),msg.isMark(),msg.isTempcheck(),
					msg.isReadcheck(),msg.isDelcheck()));
		}
		return dtolist;
		
	}
	
	
	
	//임시보관 기능
	public void changeTempcheck(int num) {
		
		Msg msg = dao.findById(num).orElse(null);	
		int temp = (msg.isTempcheck()) ? 1:0 ;			
		if(temp==1) {
			dao.minusTemp(num);
		}else {
			dao.plusTemp(num);
		}
	}
	
	//즐겨찾기 기능
	public void changeMark(int num) {
		Msg msg = dao.findById(num).orElse(null);	
		int mark = (msg.isMark()) ? 1:0 ;			
		if(mark==1) {
			dao.minusMark(num);
		}else {
			dao.plusMark(num);
		}
	}
	
	// 읽음 기능
	public void changeReadcheck(int num) {
	
		Msg msg = dao.findById(num).orElse(null);	
		int read = (msg.isReadcheck()) ? 1:0 ;			
		if(read==1) {
			dao.minusRead(num);
		}else {
			dao.plusRead(num);
		}
	}
	
	// 휴지통 기능 
	public void changeDelcheck(int num) {
			
		Msg msg = dao.findById(num).orElse(null);	
		int del = (msg.isDelcheck()) ? 1:0 ;			
		if(del==1) {
			dao.minusDel(num);
		}else {
			dao.plusDel(num);
		}
	}	
	
	// 글 번호로 완전 삭제
	public void delete(int num) {
		dao.deleteById(num);
	}
	
	// 수신자로 완전 삭제
	public void deleteAllByReceiver(StoreDto receiver) {
		dao.deleteByReceiver(receiver);
	}
	
	
	//=========================================================
	// 전체 메일 수
	public long countAllBySenderAndReceiver(StoreDto store){
		return dao.countAll(store);
	}
	
	// 전체 메일 중에서 읽지 않은 메일 수
	public long countAllBySenderAndReceiverAndRead(StoreDto store){
		return dao.countAllRead(store);
	}
	
	//=========================================================
	
	
	//=========================================================
	// 즐찾에 있는 메일 전체 수
	public long countBy(StoreDto store){
		return dao.countByMark(store);
	}
	
	// 즐찾에 있는 메일 중에서 읽지 않은 메일 수
	public long countByDelRead(StoreDto store){
		return dao.countByDelRead(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================
	
	
	
	//=========================================================
	// 받은 메일함에 있는 메일 전체 수
	public long countAllReceiver(String store){
		return dao.countAll(store);
	}
	
	// 받은 메일함에 있는 메일 중에서 읽지 않은 메일 수
	public long countAllReadReceiver(StoreDto store){
		return dao.countAllRead(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================
	
	//=========================================================
	// 보낸 메일에 있는 메일 전체 수
	public long countByVender(StoreDto store){
		return dao.countByVender(store);
	}
	
	// 보낸 메일에 있는 메일 중에서 읽지 않은 메일 수
	public long countByVenderTemp(StoreDto store){
		return dao.countByVenderTemp(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================

	
	//=========================================================
	// 임시 보관에 있는 메일 전체 수
	public long countByD(StoreDto store){
		return dao.countByTemp(store);
	}
	
	// 임시 보관에 있는 메일 중에서 읽지 않은 메일 수
	public long countByDelRe(StoreDto store){
		return dao.countByTempRead(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================
	
	
	//=========================================================
	// 휴지통에 있는 메일 전체 수
	public long countByDel(StoreDto store){
		return dao.countByDelcheck(store);
	}
	
	// 휴지통에 있는 메일 중에서 읽지 않은 메일 수
	public long countBybyDelReads(StoreDto store){
		return dao.countByDelRead(store);
	}
	// 메일 이름과 날짜가 나오는 폼은 findall + for if문으로 해결 
	
	//=========================================================
	
	
	
	
	
	 
	
	
	
	
}
