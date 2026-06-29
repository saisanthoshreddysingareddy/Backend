package com.example.scaler.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scaler.models.ScheduledLecture;

public interface ScheduledLectureRepository extends JpaRepository<ScheduledLecture, Long>{
    List<ScheduledLecture> findAllByBatchIdOrderByLectureStartTime(Long batchId);

}
