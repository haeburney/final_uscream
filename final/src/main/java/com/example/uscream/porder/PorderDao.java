package com.example.uscream.porder;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.uscream.store.Store;

import jakarta.transaction.Transactional;

@Repository
public interface PorderDao extends JpaRepository<Porder, Integer>{
	ArrayList<Porder> findByOrdernum(String ordernum);
	ArrayList<Porder> findByOrdernumAndStoreid(String ordernum, Store storeid);
	
	
	@Modifying
	@Transactional
	@Query(value = "update Order set confirm = 1 where tempnum = :tempnum",nativeQuery = true)
	void confirm(@Param("tempnum") int tempnum);
}
