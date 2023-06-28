package com.example.uscream.porder;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.uscream.store.Store;

import jakarta.transaction.Transactional;

@Repository
public interface PorderDao extends JpaRepository<Porder, Integer>{
	ArrayList<Porder> findByOrdernumOrderByTempnum(String ordernum);
	ArrayList<Porder> findByOrdernumAndStoreid(String ordernum, Store storeid);
	
	
	@Modifying
	@Transactional
	@Query(value = "update Order set confirm = 1 where tempnum = :tempnum",nativeQuery = true)
	void confirm(@Param("tempnum") int tempnum);
	
	@Modifying
	@Transactional
	@Query(value = "SELECT p.ordernum, SUM(p.ordercost) AS totalcost, TO_CHAR(MIN(p.orderdate), 'YYYYMMDD') AS orderdate, TO_CHAR(MAX(p.confirmdate), 'YYYYMMDD') AS confirmdate, p.store, p.checkconfirm "
			+ "FROM Porder p "
			+ "GROUP BY p.ordernum, p.store, p.checkconfirm"
			,nativeQuery = true)
    ArrayList<Map<String, String>> findDistinctOrdernums();
	
	@Modifying
	@Transactional
	@Query(value = "SELECT p.ordernum, SUM(p.ordercost) AS totalcost, TO_CHAR(MIN(p.orderdate), 'YYYYMMDD') AS orderdate, TO_CHAR(MAX(p.confirmdate), 'YYYYMMDD') AS confirmdate, p.store, p.checkconfirm "
			+ "FROM Porder p where store = :store "
			+ "GROUP BY p.ordernum, p.store, p.checkconfirm"
			,nativeQuery = true)
	ArrayList<Map<String, String>> findDistinctOrdernumsByStore(@Param("store") String store);
	
	
}
