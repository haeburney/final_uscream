package com.example.uscream.porder;

import java.util.ArrayList;
import java.util.List;
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
	ArrayList<Porder> findByOrdernum(String ordernum);
	ArrayList<Porder> findByOrdernumAndStoreid(String ordernum, Store storeid);
	
	
	@Modifying
	@Transactional
	@Query(value = "update Order set confirm = 1 where tempnum = :tempnum",nativeQuery = true)
	void confirm(@Param("tempnum") int tempnum);
	
	@Modifying
	@Transactional
	@Query(value = "SELECT ordernum, totalcost,to_char(orderdate,'yy/mm/dd') as orderdate, to_char(confirmdate,'yy/mm/dd') as confirmdate, checkconfirm FROM (SELECT ordernum, SUM(ordercost) "
			+ "AS totalcost, MIN(orderdate) AS orderdate, MAX(confirmdate) AS confirmdate, checkconfirm FROM Porder GROUP BY ordernum, checkconfirm)",
			nativeQuery = true)
    ArrayList<Map<String, String>> findDistinctOrdernums();
}
