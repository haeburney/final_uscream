package com.example.uscream.msg;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.uscream.store.Store;



@Repository
public interface MsgDao extends JpaRepository<Msg, Integer> {
	ArrayList<Msg> findBySenderLike(Store store);	//보낸 사람으로 검색 (받은 메세지함에서 검색용)
	ArrayList<Msg> findBySender(Store store);		//보낸 사람으로 검색 (내가 보낸 메세지)
	ArrayList<Msg> findByReceiver(Store store);		//내가 받은 메일 검색 
	ArrayList<Msg> findByTitleLike(String title);
	
	@Modifying
	@Transactional
	@Query(value="delete * from msg where and receiver = :Store ", nativeQuery=true)
	ArrayList<Msg> deleteByReceiver(@Param("Store") Store receiver);
	
	
	//전체 즐찾 조회 
	@Query(value="select count(*)  from msg where mark = 1 and receiver=:Store", nativeQuery=true)
	long countByMark(@Param("Store") Store store);
	// 전체 즐찾 중 읽지 않은 메시지 
	@Query(value="select count(*)  from msg where mark = 1 and readcheck = 1 and receiver=:Store", nativeQuery=true)
	long countByMarkRead(@Param("Store") Store store);
	
	
	// 수신자로 메일 수 조회 
	@Query(value="select count(*)  from msg where receiver = :Store", nativeQuery=true)
	long countAll(@Param("Store") Store store);
	// 수신자 메일 중 읽지 않은 메시지 
	@Query(value="select count(*)  from msg where receiver = :Store and readcheck = 0 ", nativeQuery=true)
	long countAllRead(@Param("Store") Store store);
	
	
	//보낸 메일 메시지 
	@Query(value="select count(*)  from msg where sender = :Store ", nativeQuery=true)
	long countByVender(@Param("Store") Store store);
	//보낸 메일 중 읽지 않은 메시지 
	@Query(value="select count(*)  from msg where tempcheck =1 sender =:Store ", nativeQuery=true)
	long countByVenderTemp(@Param("Store") Store store);
			
	// 임시보관 전체
	@Query(value="select count(*)  from msg where tempcheck = 1 and sender = :Store", nativeQuery=true)
	long countByTemp(@Param("Store") Store store);
	// 임시보관에서 읽지 않은 메세지
	@Query(value="select count(*)  from msg where tempcheck = 1 and readcheck = 0 and sender = :Store ", nativeQuery=true)
	long countByTempRead(@Param("Store") Store store);
		
		
	// 휴지통 전체
	@Query(value="select count(*)  from msg where delcheck = 1 and receiver = :Store", nativeQuery=true)
	long countByDelcheck(@Param("Store") Store receiver);
	// 휴지통에서 읽지 않은 메시지  
	@Query(value="select count(*) from msg where delcheck = 1 and readcheck = 0 and receiver = :Store", nativeQuery=true)
	long countByDelRead(@Param("Store") Store receiver);
	
	//account.sender ,, account.receiver = > loginId ; 
	
	
	
}	
	