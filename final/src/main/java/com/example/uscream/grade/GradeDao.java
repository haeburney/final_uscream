package com.example.uscream.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeDao extends JpaRepository<Grade, Integer> {

}
