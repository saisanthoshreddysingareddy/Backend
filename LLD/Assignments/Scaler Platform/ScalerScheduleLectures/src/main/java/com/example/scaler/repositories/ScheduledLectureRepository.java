package com.example.scaler.repositories;

import com.example.scaler.models.ScheduledLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduledLectureRepository extends JpaRepository<ScheduledLecture, Long> {
    Optional<ScheduledLecture> findTopByBatchIdOrderByLectureStartTimeDesc(Long batchId);

}
