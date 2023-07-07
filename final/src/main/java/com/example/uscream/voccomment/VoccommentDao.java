package com.example.uscream.voccomment;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.uscream.store.Store;
import com.example.uscream.voc.Voc;

public interface VoccommentDao extends JpaRepository<Voccomment, Integer> {

	ArrayList<Voccomment> findByVocnum(Voc vocnum);
}
