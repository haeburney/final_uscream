package com.example.uscream.msg;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.uscream.store.Store;
import com.example.uscream.store.StoreDto;



@Repository
public interface MsgDao extends JpaRepository<Msg, Integer> {
	ArrayList<Msg> findBySenderLike(StoreDto store);	//보낸 사람으로 검색 (받은 메세지함에서 검색용)
	ArrayList<Msg> findBySender(StoreDto store);		//보낸 사람으로 검색 (내가 보낸 메세지)
	ArrayList<Msg> findByReceiver(StoreDto store);		//내가 받은 메일 검색 
	ArrayList<Msg> findByTitleLike(String title);
	
	
	@Modifying
	@Transactional
	@Query(value="delete * from msg where and receiver = :StoreDto ", nativeQuery=true)
	ArrayList<Msg> deleteByReceiver(@Param("StoreDto") StoreDto receiver);
	
	// 읽음 처리 기능
	@Modifying
	@Transactional
	@Query(value="update msg set readcheck = 1 where msgnum=:msgnum", nativeQuery=true)
	int plusRead(@Param("msgnum") int num);
	@Modifying
	@Transactional
	@Query(value="update msg set readcheck = 0 where msgnum=:msgnum", nativeQuery=true)
	int minusRead(@Param("msgnum") int num);
	
	// 즐겨찾기 기능
	
	@Modifying
	@Transactional
	@Query(value="update msg set mark = 1 where msgnum=:msgnum", nativeQuery=true)
	int plusMark(@Param("msgnum") int num);
	@Modifying
	@Transactional
	@Query(value="update msg set mark = 0 where msgnum=:msgnum", nativeQuery=true)
	int minusMark(@Param("msgnum") int num);
	//전체 즐찾 조회 
	@Query(value="select count(*)  from msg where mark = 1 and receiver=:StoreDto", nativeQuery=true)
	long countByMark(@Param("StoreDto") StoreDto store);
	// 전체 즐찾 중 읽지 않은 메시지 
	@Query(value="select count(*)  from msg where mark = 1 and readcheck = 1 and receiver=:StoreDto", nativeQuery=true)
	long countByMarkRead(@Param("StoreDto") StoreDto store);
	
	
	// 수신자로 메일 수 조회 
	@Query(value="select count(*)  from msg where receiver = :StoreDto", nativeQuery=true)
	long countAll(@Param("StoreDto") StoreDto store);
	// 수신자 메일 중 읽지 않은 메시지 
	@Query(value="select count(*)  from msg where receiver = :StoreDto and readcheck = 0 ", nativeQuery=true)
	long countAllRead(@Param("StoreDto") StoreDto store);
	
	
	//보낸 메일 메시지 
	@Query(value="select count(*)  from msg where sender = :StoreDto ", nativeQuery=true)
	long countByVender(@Param("StoreDto") StoreDto store);
	//보낸 메일 중 읽지 않은 메시지 
	@Query(value="select count(*)  from msg where tempcheck =1 sender =:StoreDto ", nativeQuery=true)
	long countByVenderTemp(@Param("StoreDto") StoreDto store);
			
	//임시보관 기능
	@Modifying
	@Transactional
	@Query(value="update msg set tempcheck = 1 where msgnum=:msgnum", nativeQuery=true)
	int plusTemp(@Param("msgnum") int num);
	@Modifying
	@Transactional
	@Query(value="update msg set tempcheck = 0 where msgnum=:msgnum", nativeQuery=true)
	int minusTemp(@Param("msgnum") int num);
	// 임시보관 전체
	@Query(value="select count(*)  from msg where tempcheck = 1 and sender = :StoreDto", nativeQuery=true)
	long countByTemp(@Param("StoreDto") StoreDto store);
	// 임시보관에서 읽지 않은 메세지
	@Query(value="select count(*)  from msg where tempcheck = 1 and readcheck = 0 and sender = :StoreDto ", nativeQuery=true)
	long countByTempRead(@Param("StoreDto") StoreDto store);
		
	// 휴지통 기능
	@Modifying
	@Transactional
	@Query(value="update msg set delcheck = 1 where msgnum=:msgnum", nativeQuery=true)
	int plusDel(@Param("msgnum") int num);
	@Modifying
	@Transactional
	@Query(value="update msg set delcheck = 0 where msgnum=:msgnum", nativeQuery=true)
	int minusDel(@Param("msgnum") int num);
	// 휴지통 전체
	@Query(value="select count(*)  from msg where delcheck = 1 and receiver = :StoreDto", nativeQuery=true)
	long countByDelcheck(@Param("StoreDto") StoreDto receiver);
	// 휴지통에서 읽지 않은 메시지  
	@Query(value="select count(*) from msg where delcheck = 1 and readcheck = 0 and receiver = :StoreDto", nativeQuery=true)
	long countByDelRead(@Param("StoreDto") StoreDto receiver);
	
	
}	
	