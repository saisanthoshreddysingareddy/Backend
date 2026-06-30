package com.example.scaler.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scaler.models.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
}
