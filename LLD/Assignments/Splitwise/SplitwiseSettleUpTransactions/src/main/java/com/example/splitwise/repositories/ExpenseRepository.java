package com.example.splitwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.splitwise.models.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
