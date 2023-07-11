package com.example.uscream.store;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDao extends JpaRepository<Store, String> {

	ArrayList<Store> findByStorenameLike(String storename);
	Store findByManagernameContaining(String managername);
	ArrayList<Store> findByAccounttype(int accounttype);
}
