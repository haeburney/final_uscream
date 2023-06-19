package com.example.uscream.sellproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellProductDao extends JpaRepository<SellProduct, Integer>{
	
	
}
