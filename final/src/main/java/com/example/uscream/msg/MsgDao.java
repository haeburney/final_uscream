package com.example.uscream.msg;
	
	
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.uscream.account.Account;
	
@Repository
public interface MsgDao extends JpaRepository<Msg, Integer> {
	ArrayList<Msg> findAllOrderByDateAsc();
	ArrayList<Msg> findBySenderLike(String name);	//보낸 사람으로 검색
	ArrayList<Msg> findByReceiver(String name);		//내가 받은 메일 검색 
	ArrayList<Msg> findByTitleLike(String title);
	ArrayList<Msg> findAll(String title);
	ArrayList<Msg> findByReceiverAndSender(Account account);	
	
	@Modifying
	@Transactional
	@Query(value="delete * from msg where and receiver = ? ", nativeQuery=true)
	ArrayList<Msg> deleteByReceiver(Account receiver);
	
	
	//전체 즐찾 조회 
	@Query(value="select count(*)  from msg where mark = 1 ", nativeQuery=true)
	long countByMark();
	// 전체 즐찾 중 읽지 않은 메시지 
	@Query(value="select count(*)  from msg where mark = 1 and readcheck = 1 ", nativeQuery=true)
	long countByMarkRead();
	
	
	// 수신자로 메일 수 조회 
	@Query(value="select count(*)  from msg where receiver = :", nativeQuery=true)
	long countAll(@Param("") );
	// 수신자 메일 중 읽지 않은 메시지 
	@Query(value="select count(*)  from msg where receiver = : and readcheck = 0 ", nativeQuery=true)
	long countAllRead(@Param(""));
	
	
	//보낸 메일 메시지 
	@Query(value="select count(*)  from msg where vender = :account.vender ", nativeQuery=true)
	long countByVender(@Param("account") Account account);
	//보낸 메일 중 읽지 않은 메시지 
	@Query(value="select count(*)  from msg where tempcheck =1 vender =:account.vender ", nativeQuery=true)
	long countByVenderTemp(@Param("account") Account account);
	
	
	// 임시보관 전체
	@Query(value="select count(*)  from msg where tempcheck = 1 and sender = :account.sender ", nativeQuery=true)
	long countByTemp(@Param("account") Account account);
	// 임시보관에서 읽지 않은 메세지
	@Query(value="select count(*)  from msg where tempcheck = 1 and readcheck = 0 and sender = :account.sender ", nativeQuery=true)
	long countByTempRead(@Param("account") Account account);

		
	// 휴지통 전체
	@Query(value="select count(*)  from msg where delcheck = 1 and receiver = :account", nativeQuery=true)
	long countByDelcheck(@Param("account") Account receiver);
	// 휴지통에서 읽지 않은 메시지  
	@Query(value="select count(*) from msg where delcheck = 1 and readcheck = 0 and receiver = :account", nativeQuery=true)
	long countByDelRead(@Param("account") Account account);
	
	//account.sender ,, account.receiver = > loginId ; 
	
	
	
}	
	