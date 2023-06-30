package com.example.uscream.porder;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.annotations.Parent;
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
	@Query(value = "update Porder set checkconfirm = :num, confirmdate= sysdate where tempnum = :tempnum",nativeQuery = true)
	void confirm(@Param("tempnum") int tempnum, @Param("num") int num);
	
	@Modifying
	@Transactional
	@Query(value = "update Porder set amount = :amount, ordercost= :ordercost where tempnum = :tempnum",nativeQuery = true)
	void updateamount(@Param("tempnum") int tempnum, @Param("amount") int amount, @Param("ordercost") int ordercost);
	
	@Modifying
	@Transactional
	@Query(value = "SELECT p.ordernum, SUM(p.ordercost) AS totalcost, TO_CHAR(MIN(p.orderdate), 'YYYYMMDD') AS orderdate, TO_CHAR(MAX(p.confirmdate), 'YYYYMMDD') AS confirmdate, p.store, "
	        + "CASE "
	        + "    WHEN p.checkconfirm = 0 THEN '처리중' "
	        + "    WHEN p.checkconfirm IN (1, 2) THEN '처리완료' "
	        + "END AS status, "
	        + "SUM(CASE WHEN p.checkconfirm = 1 THEN p.ordercost ELSE 0 END) AS ordercost "
	        + "FROM Porder p "
	        + "WHERE p.checkconfirm IN (0, 1, 2) "
	        + "GROUP BY p.ordernum, p.store, CASE "
	        + "    WHEN p.checkconfirm = 0 THEN '처리중' "
	        + "    WHEN p.checkconfirm IN (1, 2) THEN '처리완료' "
	        + "END"
	        , nativeQuery = true)
    ArrayList<Map<String, String>> findDistinctOrdernums();
	
	@Modifying
	@Transactional
	@Query(value = "SELECT p.ordernum, SUM(p.ordercost) AS totalcost, TO_CHAR(MIN(p.orderdate), 'YYYYMMDD') AS orderdate, TO_CHAR(MAX(p.confirmdate), 'YYYYMMDD') AS confirmdate, p.store, "
	        + "CASE "
	        + "    WHEN p.checkconfirm = 0 THEN '처리중' "
	        + "    WHEN p.checkconfirm IN (1, 2) THEN '처리완료' "
	        + "END AS status, "
	        + "SUM(CASE WHEN p.checkconfirm = 1 THEN p.ordercost ELSE 0 END) AS ordercost "
	        + "FROM Porder p "
	        + "WHERE store = :store and p.checkconfirm IN (0, 1, 2) "
	        + "GROUP BY p.ordernum, p.store, CASE "
	        + "    WHEN p.checkconfirm = 0 THEN '처리중' "
	        + "    WHEN p.checkconfirm IN (1, 2) THEN '처리완료' "
	        + "END"
	        , nativeQuery = true)
	ArrayList<Map<String, String>> findDistinctOrdernumsByStore(@Param("store") String store);
	
	@Modifying
	@Transactional
	@Query(value = "SELECT EXTRACT(YEAR FROM confirmdate) AS year, EXTRACT(MONTH FROM confirmdate) AS month, store AS storeid, SUM(ordercost) AS monthly_total "
		+"FROM porder "
		+"WHERE EXTRACT(YEAR FROM confirmdate) = :year "
		   +"AND EXTRACT(MONTH FROM confirmdate) = :month "
		    +"AND store = :store and checkconfirm=1 "
		    +"GROUP BY EXTRACT(YEAR FROM confirmdate),EXTRACT(MONTH FROM confirmdate), store, checkconfirm"
			,nativeQuery = true)
	ArrayList<Map<String, String>> findMonthlyOrderCost(@Param("store") String storeid, @Param("year") int year, @Param("month") int month);
	
	
}
