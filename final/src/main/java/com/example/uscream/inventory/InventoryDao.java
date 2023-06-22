package com.example.uscream.inventory;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uscream.store.Store;

@Repository
public interface InventoryDao extends JpaRepository<Inventory, Integer> {
	ArrayList<Inventory> findByStoreid(Store storeid);
}
