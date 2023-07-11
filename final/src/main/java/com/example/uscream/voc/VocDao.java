package com.example.uscream.voc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uscream.store.Store;
import com.example.uscream.voccomment.Voccomment;

@Repository
public interface VocDao extends JpaRepository<Voc, Integer> {

	ArrayList<Voc> findByCategory(int category);
	
	ArrayList<Voc> findByStoreid(Store storeid);
	
	ArrayList<Voc> findAllByOrderByWdateDesc();
	
	ArrayList<Voc> findByStoreidOrderByWdateDesc(Store storeid);
}