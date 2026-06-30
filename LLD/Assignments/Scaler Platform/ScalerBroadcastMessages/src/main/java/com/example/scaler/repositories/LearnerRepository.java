package com.example.scaler.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scaler.models.Learner;

@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {
}
