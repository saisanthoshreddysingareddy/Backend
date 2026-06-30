package com.example.scaler.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scaler.models.Communication;

@Repository
public interface CommunicationRepository
        extends JpaRepository<Communication, Long> {
}