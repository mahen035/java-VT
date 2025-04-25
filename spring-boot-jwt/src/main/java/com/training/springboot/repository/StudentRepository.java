package com.training.springboot.repository;

import com.training.springboot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByStudName(String studName);

    //Query Methods
}
