package com.example.scaler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scaler.models.Batch;

public interface BatchRepository  extends JpaRepository<Batch, Long>{
}
