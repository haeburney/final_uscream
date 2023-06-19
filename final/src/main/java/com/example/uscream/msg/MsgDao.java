package com.example.uscream.msg;
	
	
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.uscream.account.Account;
	
@Repository
public interface MsgDao extends JpaRepository<Msg, Integer> {
	ArrayList<Msg> findAllOrderByDateAsc();
	ArrayList<Msg> findBySenderLike(String name);	//보낸 사람으로 검색
	ArrayList<Msg> findByReceiver(String name);		//내가 받은 메일 검색 
	ArrayList<Msg> findByTitleLike(String title);
	
	@Modifying
	@Transactional
	@Query(value="delete * from msg where and receiver = ? ", nativeQuery=true)
	ArrayList<Msg> deleteByReceiver(Account receiver);
	
	
	
	
	
	
	
	// 휴지통 전체
	@Query(value="count * from msg where delcheck = 1 and vender = ? ", nativeQuery=true)
	int countByDelcheck(Account account);
	// 휴지통에서 읽지 않은 메시지  
	@Query(value="count * from msg where delcheck = 1 and readcheck = 0 and vender = ? ", nativeQuery=true)
	int countByDelandRead(Account account);
	
	
	// 임시보관 전체
	@Query(value="count * from msg where tempcheck = 0 and vender = ? ", nativeQuery=true)
	int countByTemp(Account account);
	// 임시보관에서 읽지 않은 메세지
	@Query(value="count * from msg where tempcheck = 1 and readcheck = 0 and vender = ? ", nativeQuery=true)
	int countByTempRead(Account account);
	
	
	
	@Query(value="count * from msg where tempcheck = 1 and vender = ? ", nativeQuery=true)
	int countByTempcheck(Account account);
	
}	
	