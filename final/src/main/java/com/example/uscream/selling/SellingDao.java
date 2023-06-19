package com.example.uscream.selling;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uscream.sellproduct.SellProduct;
import com.example.uscream.store.Store;

@Repository
public interface SellingDao extends JpaRepository<Selling, Integer> {
	ArrayList<Selling> findByStoreid(Store storeid);
	ArrayList<Selling> findBySellproduct(SellProduct product);
	ArrayList<Selling> findBySellingDate(Date sellingdate);

}
