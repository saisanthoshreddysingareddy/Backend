package com.example.scaler.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scaler.models.Batch;
import com.example.scaler.models.ScheduledLecture;

public interface ScheduledLectureRepository extends JpaRepository<ScheduledLecture, Long> {

    List<ScheduledLecture>
    findByBatchAndLectureStartTimeBetween(
            Batch batch,
            Date start,
            Date end);

    List<ScheduledLecture>
    findByBatchAndLectureStartTimeGreaterThanEqual(
            Batch batch,
            Date start);
}
