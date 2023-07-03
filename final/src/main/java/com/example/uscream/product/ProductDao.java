package com.example.uscream.product;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product,Integer>{
	ArrayList<Product> findByProductname(String productname);
	
	ArrayList<Product> findByProductcategory(String productcategory);
	
}
