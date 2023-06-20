package com.example.uscream.emp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpDao extends JpaRepository<Emp, Integer> {
	// ArrayList<Emp> findByStoreId(Store storeid);
}
