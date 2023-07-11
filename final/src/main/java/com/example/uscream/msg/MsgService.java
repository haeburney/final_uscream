package com.example.uscream.msg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgService {

	private MsgDao dao;

	@Autowired
	public MsgService(MsgDao dao) {
		super();
		this.dao = dao;
	}

	// 생성 및 수정
	public ArrayList<MsgDto> save(MsgDto msgdto) {
		
		ArrayList<MsgDto> real = new ArrayList<MsgDto>();
		msgdto.setReadcheck(true);
		msgdto.setReal(true);
		Msg msg = dao.save(new Msg(msgdto.getMsgnum(), msgdto.getSender(), msgdto.getReceiver(), msgdto.getTitle(),
				msgdto.getMsgdate(), msgdto.getContent(), msgdto.getMsgfile(), msgdto.getReply(), msgdto.isMark(),
				msgdto.isTempcheck(), msgdto.isReadcheck(), msgdto.isDelcheck(), msgdto.isReal()));

		msgdto.setReal(false);
		Msg msg2 = dao.save(new Msg(msgdto.getMsgnum(), msgdto.getSender(), msgdto.getReceiver(), msgdto.getTitle(),
				msgdto.getMsgdate(), msgdto.getContent(), msgdto.getMsgfile(), msgdto.getReply(), msgdto.isMark(),
				msgdto.isTempcheck(), msgdto.isReadcheck(), msgdto.isDelcheck(), msgdto.isReal()));

		MsgDto dto = new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(), msg.getMsgdate(),
				msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(), msg.isTempcheck(),
				msg.isReadcheck(), msg.isDelcheck(), msg.isReal());

		MsgDto dto2 = new MsgDto(msg2.getMsgnum(), msg2.getSender(), msg2.getReceiver(), msg2.getTitle(),
				msg2.getMsgdate(), msg2.getContent(), msg2.getMsgfile(), null, msg2.getReply(), msg2.isMark(),
				msg2.isTempcheck(), msg2.isReadcheck(), msg2.isDelcheck(), msg2.isReal());

		real.add(dto);
		real.add(dto2);

		return real;
	}
	
	public MsgDto saveTemp(MsgDto msgdto) {
		msgdto.setTempcheck(true);
		msgdto.setReal(true);
		msgdto.setReadcheck(true);
		Msg msg = dao.save(new Msg(msgdto.getMsgnum(), msgdto.getSender(), msgdto.getReceiver(), msgdto.getTitle(),
				msgdto.getMsgdate(), msgdto.getContent(), msgdto.getMsgfile(), msgdto.getReply(), msgdto.isMark(),
				msgdto.isTempcheck(), msgdto.isReadcheck(), msgdto.isDelcheck(), msgdto.isReal()));
	
		return  new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(), msg.getMsgdate(),
				msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(), msg.isTempcheck(),
				msg.isReadcheck(), msg.isDelcheck(), msg.isReal());
	}

	// 메일 하나 조회
	public MsgDto getMsg(int num) {
		Msg msg = dao.findById(num).orElse(null);
		if (msg == null) {

			return null;
		}
		return new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(), msg.getMsgdate(),
				msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(), msg.isTempcheck(),
				msg.isReadcheck(), msg.isDelcheck(), msg.isReal());
	}

	// 이름을 두 개 받아 메일 하나를 조회하는 메서드 
	public MsgDto getMsges(String sender, String receiver) {
		
		
		
		return null;
	}
	

	

//	============ boolean 값 변경하는 서비스=============================

	// 임시보관
	public void changeTempcheck(int num) {
		Msg msg = dao.findById(num).orElse(null);
		int temp = (msg.isTempcheck()) ? 1 : 0;

		if (temp == 1) {
			dao.minusTemp(num);
		} else {
			dao.plusTemp(num);
		}
	}

	// 즐겨찾기 기능
	public void changeMark(int num) {
		Msg msg = dao.findById(num).orElse(null);
		int mark = (msg.isMark()) ? 1 : 0;
		if (mark == 1) {
			dao.minusMark(num);
		} else {
			dao.plusMark(num);
		}
	}

	// 읽음 기능
	public void changeReadcheck(int num) {

		Msg msg = dao.findById(num).orElse(null);
		int read = (msg.isReadcheck()) ? 1 : 0;
		if (read == 1) {
			dao.minusRead(num);
		} else {
			dao.plusRead(num);
		}
	}
	
	// 디테일용 읽음 기능
		public void fixReadcheck(int num) {

			Msg msg = dao.findById(num).orElse(null);
			dao.minusRead(num);
		}
		

	// 휴지통 기능
	public void changeDelcheck(int num) {

		Msg msg = dao.findById(num).orElse(null);
		int del = (msg.isDelcheck()) ? 1 : 0;
		if (del == 1) {
			dao.minusDel(num);
		} else {
			dao.plusDel(num);
		}
	}

// ==================메세지 조회하는 기능=====================================

	// SideBar

	// 보낸 메세지, 받은 메세지, 임시보관 메세지에서 읽지 않은 수 조회
	public long countSideBarReadMsg(String name) {
		long number1 = dao.countByReadReceiveMsg(name);
		long number2 = dao.countByReadSendMsg(name);
		long number3 = dao.countByReadTempMsg(name);
		long result = number1 + number2 + number3;
	
		return result;
	}

	public long countSideBarMarkMsg(String name) {
		long number1 = dao.countByMarkReceiveMsg(name);
		long number2 = dao.countByMarkSendMsg(name);
		long number3 = dao.countByMarkTempMsg(name);
		long result = number1 + number2 + number3;
		
		return result;
	}

	// ReceiveMsg

	// 받은 메세지 전체 수 조회
	public Map countAllReceiverMsg(String receiver) {
		long number1 = dao.countByReadReceiveMsg(receiver);
		long number2 = dao.countAllByReadReceiveMsg(receiver);
		Map map = new HashMap();
		map.put("countByReadReceiveMsg", number1);
		map.put("countAllByReadReceiveMsg", number2);

		return map;
	}

	// 받은 메세지 전체 행 조회
	public ArrayList<MsgDto> selectAllReceiveMsg(String receiver) {
		ArrayList<Msg> msglist = dao.findAllByReadReceiveMsg(receiver);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();

		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
					msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
					msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
		}
		return dtolist;
	}

	// 받은 메세지에서 보낸 사람으로 검색
	public ArrayList<MsgDto> selectBySender(String sender, String receiver) {
		ArrayList<Msg> msglist = dao.findBySender(sender, receiver);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
					msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
					msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
		}
		return dtolist;
	}

	// SendMsg

	// 보낸 메세지 전체 수 조회
	public Map countAllSendMsg(String sender) {
		long number1 = dao.countByReadSendMsg(sender);
		long number2 = dao.countAllByReadSendMsg(sender);
		Map map = new HashMap();
		
		map.put("countByReadSendMsg", number1);
		map.put("countAllByReadSendMsg", number2);

		return map;
	}

	// 보낸 메세지 전체 행 조회
	public ArrayList<MsgDto> selectAllSendMsg(String sender) {
		ArrayList<Msg> msglist = dao.findAllByReadSendMsg(sender);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();

		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
					msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
					msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
		}
		return dtolist;
	}

	// 보낸 메세지에서 받은 사람으로 검색
	public ArrayList<MsgDto> selectByReceiver(String sender, String receiver) {
		ArrayList<Msg> msglist = dao.findByReceiver(sender, receiver);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();

		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
					msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
					msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
		}
		return dtolist;
	}

	// TempMsg

	// 보낸 메세지에서 temp 전체 수 조회
	public Map countAllByTempMsg(String sender) {
		long number1 = dao.countByReadTempMsg(sender);
		long number2 = dao.countAllByTempMsg(sender);
		Map map = new HashMap();

		map.put("countByReadTempMsg", number1);
		map.put("countAllByTempMsg", number2);

		return map;
	}

	// 보낸 메세지 temp 전체 행 조회
	public ArrayList<MsgDto> selectAllTempMsg(String sender) {
		ArrayList<Msg> msglist = dao.findAllByTempMsg(sender);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();

		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
					msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
					msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
		}
		return dtolist;
	}

	// 임시보관 메세지에서 받을 사람으로 검색
	public ArrayList<MsgDto> selectByReceiverTemp(String sender, String receiver) {
		ArrayList<Msg> msglist = dao.findByReceiverTemp(sender, receiver);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
		for (Msg msg : msglist) {
			dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
					msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
					msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
		}
		return dtolist;
	}
	
	//MarkMsg
	
	// 즐찾에서 읽지 않은 메세지 수
	public long countByReadAndMarkRead (String name) {
		
		long number1 = dao.countByMarkAndReadReceiveMsg(name);
		long number2 = dao.countByMarkAndReadSendMsg(name);
		long number3 = dao.countByMarkAndReadTempMsg(name);
		long result = number1 + number2 + number3;
		
		return result;
	}
	
	// 즐찾에 있는 모든 메세지 조회 
	public ArrayList<MsgDto> selectAllMark(String name){
		ArrayList<Msg> list1 = dao.findAllByMarkReceiveMsg(name);
		ArrayList<Msg> list2 = dao.findAllByMarkSendMsg(name);
		ArrayList<Msg> list3 = dao.findAllByMarkTempMsg(name);

		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
		
		for (Msg msg : list1) {
			dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
					msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
					msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
		}
		for (Msg msg : list2) {
			dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
					msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
					msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
		}
		for (Msg msg : list3) {
			dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
					msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
					msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
		}
		
		Collections.sort(dtolist, new Comparator<MsgDto>() {
	        @Override
	        public int compare(MsgDto msg1, MsgDto msg2) {
	            try {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
	                LocalDateTime dateTime1 = LocalDateTime.parse(msg1.getMsgdate(), formatter);
	                LocalDateTime dateTime2 = LocalDateTime.parse(msg2.getMsgdate(), formatter);
	                return dateTime2.compareTo(dateTime1);
	            } catch (DateTimeParseException e) {
	               
	                return 0; 
	            }
	        }
	    });
		return dtolist;
	}
	
	//DelMsg
	
	// 휴지통에서 읽지 않은 메세지 수와 전체 메세지 수 조회
	public Map countAllByDelMsg (String sender, String receiver) {
		
		long number1 = dao.countByDelReceiverAndReadMsg(receiver);
		long number2 = dao.countByDelSenderAndReadMsg(sender);

		long countAllByDelAndReadMsg = number1 + number2;
		
		long number3 =dao.countAllByDelReceiverMsg(receiver);
		long number4 =dao.countAllByDelSenderMsg(sender);
		
		long countAllByDelMsg = number3 + number4;
		
		Map map = new HashMap();
		map.put("countAllByDelAndReadMsg", countAllByDelAndReadMsg);
		map.put("countAllByDelMsg", countAllByDelMsg);
		
		return map;
	}
	
	
	// 휴지통에서 전체 행 조회
		public ArrayList<MsgDto> selectAllDelMsg(String sender, String receiver) {
		ArrayList<Msg> msglist = dao.findAllByDelAndReceiveMsg(receiver);
		ArrayList<Msg> msglist2 = dao.findAllByDelAndSendMsg(sender);
		ArrayList<MsgDto> dtolist = new ArrayList<MsgDto>();
			for (Msg msg : msglist) {
				dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
						msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
						msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
			}
			for (Msg msg : msglist2) {
				dtolist.add(new MsgDto(msg.getMsgnum(), msg.getSender(), msg.getReceiver(), msg.getTitle(),
						msg.getMsgdate(), msg.getContent(), msg.getMsgfile(), null, msg.getReply(), msg.isMark(),
						msg.isTempcheck(), msg.isReadcheck(), msg.isDelcheck(), msg.isReal()));
			}
			
			 Collections.sort(dtolist, new Comparator<MsgDto>() {
			        @Override
			        public int compare(MsgDto msg1, MsgDto msg2) {
			            try {
			                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
			                LocalDateTime dateTime1 = LocalDateTime.parse(msg1.getMsgdate(), formatter);
			                LocalDateTime dateTime2 = LocalDateTime.parse(msg2.getMsgdate(), formatter);
			                return dateTime2.compareTo(dateTime1);
			            } catch (DateTimeParseException e) {
			                return 0; 
			            }
			        }
			    });
			 
			
			return dtolist;
		}
		
		
		
		//휴지통 완전 삭제
		public void deleteReal(int num) {
			Msg msg = dao.findById(num).orElse(null);
			// 임시보관 메세지에서 보낸 메세지로 변경될 떄
			if(msg.isTempcheck()==true) {
				dao.deleteById(num);
			}
			// 휴지통에서 진짜로 삭제될 때 
			if(msg!=null && msg.isDelcheck()==true) {
				dao.deleteById(num);
			}
		}
		
		// 휴지통 비우기 
		public int deleteAll(String name) {
			int number1 =dao.deleteAllByReceiveMsg(name);
			int number2 =dao.deleteAllBySendMsg(name);
			return number1+number2;
			
		}
}
