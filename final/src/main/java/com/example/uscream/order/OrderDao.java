package com.example.uscream.order;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.uscream.store.Store;

import jakarta.transaction.Transactional;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer>{
	ArrayList<Order> findByOrdernum(String ordernum);
	ArrayList<Order> findByOrdernumAndStoreid(String ordernum, Store storeid);
	
	
	@Modifying
	@Transactional
	@Query(value = "update Order set confirm = 1 where tempnum = :num",nativeQuery = true)
	void confirm(int tempnum);
}
