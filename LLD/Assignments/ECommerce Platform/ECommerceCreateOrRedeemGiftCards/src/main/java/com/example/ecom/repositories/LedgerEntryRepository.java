package com.example.ecom.repositories;

import com.example.ecom.models.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Integer> {
}
