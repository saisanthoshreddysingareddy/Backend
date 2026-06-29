package com.example.scaler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scaler.models.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long>{
}
