package com.example.uscream.voc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.uscream.store.Store;
import com.example.uscream.voccomment.Voccomment;

import jakarta.transaction.Transactional;

@Repository
public interface VocDao extends JpaRepository<Voc, Integer> {

	ArrayList<Voc> findByCategory(int category);
	
	ArrayList<Voc> findByStoreid(Store storeid);
	
	
	@Transactional
	@Modifying
	@Query(value="select * from (select * from voc order by wdate desc)where rownum<=3",nativeQuery=true)
	ArrayList<Voc> findAllByOrderByWdateDesc();
	
	@Transactional
	@Modifying
	@Query(value="select * from (select * from voc where store = :storeid order by wdate desc)where rownum<=3",nativeQuery=true)
	ArrayList<Voc> findByStoreidOrderByWdateDesc(@Param("storeid") String storeid);
}