package com.example.scaler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scaler.models.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long>{

}
