package com.example.scaler.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scaler.models.BatchLearner;
import com.example.scaler.models.Learner;

@Repository
public interface BatchLearnerRepository extends JpaRepository<BatchLearner, Long> {

    List<BatchLearner> findByLearner(Learner learner);
}