package com.example.uscream.msg;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.uscream.store.StoreDto;

@Repository
public interface MsgDao extends JpaRepository<Msg, Integer> {
	
	// 이름 두 개를 받아 찾는 메서드 

	@Query(value = "select * from msg where sender=:StroeDto.storeid and receiver=:StoreDto.storeid and real=1", nativeQuery = true)
	Msg findByNames(@Param("StoreDto.storeid") String sender,@Param("StoreDto.storeid") String receiver);
	
	
//	========================== boolean 값 변경 -시작-
	
	// 즐겨찾기 기능 
	@Modifying
	@Transactional
	@Query(value = "update msg set mark = 1 where msgnum=:msgnum", nativeQuery = true)
	int plusMark(@Param("msgnum") int num);

	@Modifying
	@Transactional
	@Query(value = "update msg set mark = 0 where msgnum=:msgnum", nativeQuery = true)
	int minusMark(@Param("msgnum") int num);


	// 임시보관 
	@Modifying
	@Transactional
	@Query(value = "update msg set tempcheck = 1 where msgnum=:msgnum", nativeQuery = true)
	int plusTemp(@Param("msgnum") int num);

	@Modifying
	@Transactional
	@Query(value = "update msg set tempcheck = 0 where msgnum=:msgnum", nativeQuery = true)
	int minusTemp(@Param("msgnum") int num);


	// 읽음 처리 
	@Modifying
	@Transactional
	@Query(value = "update msg set readcheck = 1 where msgnum=:msgnum", nativeQuery = true)
	int plusRead(@Param("msgnum") int num);

	@Modifying
	@Transactional
	@Query(value = "update msg set readcheck = 0 where msgnum=:msgnum", nativeQuery = true)
	int minusRead(@Param("msgnum") int num);


	// 휴지통 기능
	@Modifying
	@Transactional
	@Query(value = "update msg set delcheck = 1 where msgnum=:msgnum", nativeQuery = true)
	int plusDel(@Param("msgnum") int num);

	@Modifying
	@Transactional
	@Query(value = "update msg set delcheck = 0 where msgnum=:msgnum", nativeQuery = true)
	int minusDel(@Param("msgnum") int num);


//	========================== boolean 값 변경 -끝-
	
	
//	========================== boolean 값으로 조회 기능 -시작-
	
	
	// SideBar 

	// 1. 받은 메세지에서 읽지 않은 메세지 수 
	@Query(value = "select count(*) from msg where readcheck = 1 and receiver=:StoreDto.storeid and real=0 and delcheck=0", nativeQuery = true)
	long countByReadReceiveMsg(@Param("StoreDto.storeid") String receiver);
	// 2. 보낸 메세지에서 읽지 않은 메세지 수
	@Query(value = "select count(*) from msg where readcheck = 1 and sender=:StoreDto.storeid and real=1 and delcheck=0", nativeQuery = true)
	long countByReadSendMsg(@Param("StoreDto.storeid") String receiver);
	// 3. 임시 보관함에서 읽지 않은 메세지 수
	@Query(value = "select count(*) from msg where readcheck = 1 and tempcheck =1 and sender=:StoreDto.storeid and real=1 and delcheck=0", nativeQuery = true)
	long countByReadTempMsg(@Param("StoreDto.storeid") String receiver);
		
	// 4. 받은 메세지에서 즐겨 찾기 수 
	@Query(value = "select count(*) from msg where mark = 1 and receiver=:StoreDto.storeid and real=0 and delcheck=0", nativeQuery = true)
	long countByMarkReceiveMsg(@Param("StoreDto.storeid") String receiver);
	// 5. 보낸 메세지에서 즐겨 찾기 수 
	@Query(value = "select count(*) from msg where mark = 1 and sender=:StoreDto.storeid and real=1 and delcheck=0" , nativeQuery = true)
	long countByMarkSendMsg(@Param("StoreDto.storeid") String receiver);
	// 6. 임시 보관함에서 즐겨 찾기 수
	@Query(value = "select count(*) from msg where mark = 1 and tempcheck =1 and sender=:StoreDto.storeid and real=1 and delcheck=0", nativeQuery = true)
	long countByMarkTempMsg(@Param("StoreDto.storeid") String receiver);
	
	
	// ReceiveMsg
	
	
	// 1. 
	// 받은 메세지 전체 수 
	@Query(value = "select count(*) from msg where receiver=:StoreDto.storeid and real=0 and delcheck=0", nativeQuery = true)
	long countAllByReadReceiveMsg(@Param("StoreDto.storeid") String receiver);
	
	// 받은 메세지 행 전체 조회
	@Query(value = "select * from msg where receiver=:StoreDto.storeid and real=0 and delcheck=0 order by substr(msgdate, 1, 8) desc ", nativeQuery = true)
	ArrayList<Msg> findAllByReadReceiveMsg(@Param("StoreDto.storeid") String receiver);
	
	// sender로 검색 ((((( 실험해보기 )))))
	@Query(value = "select * from msg where sender=:StoreDto.storeid and receiver=:StoreDto.storeid and real=0 and delcheck=0 order by substr(msgdate, 1, 8) desc", nativeQuery = true)
	ArrayList<Msg> findBySender(@Param("StoreDto.storeid") String sender,@Param("StoreDto.storeid") String receiver);
	
	
	// SendMsg
	
	
	// 2. 
	// 보낸 메세지 전체 수
	@Query(value = "select count(*) from msg where sender=:StoreDto.storeid and real=1 and delcheck=0", nativeQuery = true)
	long countAllByReadSendMsg(@Param("StoreDto.storeid") String sender);
	
	// 보낸 메세지 행 전체 조회
	@Query(value = "select * from msg where sender=:StoreDto.storeid and real=1 and delcheck=0 order by substr(msgdate, 1, 8) desc", nativeQuery = true)
	ArrayList<Msg> findAllByReadSendMsg(@Param("StoreDto.storeid") String sender);
	
	// receiver로 검색 ((((( 실험해보기 )))))
	@Query(value = "select * from msg where sender=:StoreDto.storeid and receiver=:StoreDto.storeid and real=1 and delcheck=0 order by substr(msgdate, 1, 8) desc", nativeQuery = true)
	ArrayList<Msg> findByReceiver(@Param("StoreDto.storeid") String sender,@Param("StoreDto.storeid") String receiver);
	
	
	// TempMsg
	
	
	
	// 3. 
	// 임시보관 메세지 전체 수
	@Query(value = "select count(*) from msg where sender=:StoreDto.storeid and real=1 and tempcheck=1 and delcheck=0", nativeQuery = true)
	long countAllByTempMsg(@Param("StoreDto.storeid") String sender);
	
	// 임시보관 행 전체 조회
	@Query(value = "select * from msg where sender=:StoreDto.storeid and real=1 and tempcheck=1 and delcheck=0 order by substr(msgdate, 1, 8) desc", nativeQuery = true)
	ArrayList<Msg> findAllByTempMsg(@Param("StoreDto.storeid") String sender);
	
	// 임시보관에서 받는 사람으로 검색 ((((( 실험해보기 )))))
	@Query(value = "select * from msg where sender=:StoreDto.storeid and receiver=:StoreDto.storeid and real=1 and tempcheck=1 and delcheck=0 order by substr(msgdate, 1, 8) desc", nativeQuery = true)
	ArrayList<Msg> findByReceiverTemp(@Param("StoreDto.storeid") String sender,@Param("StoreDto.storeid") String receiver);
	
	
	// MarkMsg
	
	
	
	// 전체 즐찾 메세지 수 4.5.6
	// 즐찾 메세지 중 읽지 않은 메세지 수 		 
	@Query(value = "select count(*) from msg where mark = 1 and receiver=:StoreDto.storeid and readcheck = 1 and real=0 and delcheck=0", nativeQuery = true)
	long countByMarkAndReadReceiveMsg(@Param("StoreDto.storeid") String receiver);
	@Query(value = "select count(*) from msg where mark = 1 and sender=:StoreDto.storeid and readcheck = 1 and real=1 and delcheck=0", nativeQuery = true)
	long countByMarkAndReadSendMsg(@Param("StoreDto.storeid") String sender);
	@Query(value = "select count(*) from msg where mark = 1 and tempcheck =1 and sender=:StoreDto.storeid and readcheck = 1 and real=1 and delcheck=0", nativeQuery = true)
	long countByMarkAndReadTempMsg(@Param("StoreDto.storeid") String sender);
		
	// 모든 즐찾 메세지 조회  1.받은 메세지 중 즐찾 2. 보낸 메세지 중에 즐찾 3. 임시보관 중 즐찾 
	
	@Query(value = "select * from msg where receiver=:StoreDto.storeid and real=0 and mark=1", nativeQuery = true)
	ArrayList<Msg> findAllByMarkReceiveMsg(@Param("StoreDto.storeid") String receiver);
	@Query(value = "select * from msg where sender=:StoreDto.storeid and real=1 and mark=1", nativeQuery = true)
	ArrayList<Msg> findAllByMarkSendMsg(@Param("StoreDto.storeid") String sender);
	@Query(value = "select * from msg where sender=:StoreDto.storeid and real=1 and mark=1 and tempcheck=1", nativeQuery = true)
	ArrayList<Msg> findAllByMarkTempMsg(@Param("StoreDto.storeid") String sender);
		
	
	
	
	
	
	
	// DelMsg

	// 휴지통에서 읽지 않았은 메세지의 수  (내 기준이니까 받은 메세지는 0, 보낸 메세지는 1) 
	@Query(value = "select count(*) from msg where receiver=:StoreDto.storeid and real=0 and readcheck=1 and delcheck=1", nativeQuery = true)
	long countByDelReceiverAndReadMsg(@Param("StoreDto.storeid") String receiver);
	@Query(value = "select count(*) from msg where sender=:StoreDto.storeid and real=1 and readcheck=1 and delcheck=1", nativeQuery = true)
	long countByDelSenderAndReadMsg(@Param("StoreDto.storeid") String sender);

	// 휴지통 메세지 전체 수 
	@Query(value = "select count(*) from msg where receiver=:StoreDto.storeid and real=0 and delcheck=1", nativeQuery = true)
	long countAllByDelReceiverMsg(@Param("StoreDto.storeid") String receiver);
	@Query(value = "select count(*) from msg where sender=:StoreDto.storeid and real=1 and delcheck=1", nativeQuery = true)
	long countAllByDelSenderMsg(@Param("StoreDto.storeid") String sender);
	
	// 휴지통 행 전체 조회 
	@Query(value = "select * from msg where receiver=:StoreDto.storeid and real=0 and delcheck=1", nativeQuery = true)
	ArrayList<Msg> findAllByDelAndReceiveMsg(@Param("StoreDto.storeid") String receiver);
	@Query(value = "select * from msg where sender=:StoreDto.storeid and real=1 and delcheck=1", nativeQuery = true)
	ArrayList<Msg> findAllByDelAndSendMsg(@Param("StoreDto.storeid") String sender);
	
	// 완전삭제
	@Modifying
	@Transactional
	@Query(value = "delete from msg where msgnum =:msgnum  ", nativeQuery = true)
	void deleteById(@Param("msgnum") int num);	
	
	// 휴지통 비우기 (받은 메세지 기준)
	@Modifying
	@Transactional
	@Query(value = "delete from msg where receiver=:StoreDto.storeid and delcheck=1 and real=0", nativeQuery = true)
	int deleteAllByReceiveMsg(@Param("StoreDto.storeid") String receiver);	
	// 휴지통 비우기 (보낸 메세지 기준)
	@Modifying
	@Transactional
	@Query(value = "delete from msg where sender=:StoreDto.storeid and delcheck=1 and real=1", nativeQuery = true)
	int deleteAllBySendMsg(@Param("StoreDto.storeid") String sender);	
	
	// 휴지통에서 검색 기능 
	
	
	
//	========================== boolean 값으로 조회 기능 -끝-
}
