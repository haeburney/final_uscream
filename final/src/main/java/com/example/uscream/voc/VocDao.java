package com.example.uscream.voc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VocDao extends JpaRepository<Voc, Integer> {

	ArrayList<Voc> findByCategory(int category);
}