package com.example.uscream.inventory;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.uscream.store.Store;

import jakarta.transaction.Transactional;

@Repository
public interface InventoryDao extends JpaRepository<Inventory, Integer> {
	ArrayList<Inventory> findByStoreid(Store storeid);
	ArrayList<Inventory> findAllByOrderByInventorynumAsc();
	
	@Modifying
	@Transactional
	@Query(value="update inventory set amount = amount + :amount where inventorynum = :inventorynum and storeid = :storeid",nativeQuery = true )
	void addAmount(@Param("amount") int amount, @Param("inventorynum") int inventorynum , @Param("storeid") String storeid);
}
