package com.example.uscream.store;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreDao extends JpaRepository<Store, String> {

	@Query(value = "SELECT * FROM Store WHERE storename LIKE '%' || :storename || '%'", nativeQuery = true)
  ArrayList<Store> findByStorenameLike(@Param("storename") String storename);
	ArrayList<Store> findByManagernameContaining(String managername);
	ArrayList<Store> findByAccounttype(int accounttype);
	Store findByManagername(String managername);

}
