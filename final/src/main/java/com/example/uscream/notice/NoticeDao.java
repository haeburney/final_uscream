package com.example.uscream.notice;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NoticeDao extends JpaRepository<Notice, Integer> {

	//조회수 증가
	@Transactional
	@Modifying
	@Query(value="update data set cnt=cnt+1 where noticenum=:noticenum" , nativeQuery=true)
	void updateCnt(@Param("noticenum") int noticenum);
	
	//카테고리로 검색
	ArrayList<Notice> findByCategory(int category);
}