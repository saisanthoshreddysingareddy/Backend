package com.example.qcommerce.repositories;

import com.example.qcommerce.models.BatchedTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchedTaskRepository extends JpaRepository<BatchedTask, Long> {

}
