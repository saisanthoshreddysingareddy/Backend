package com.example.splitwise.repositories;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.ExpenseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {

    List<ExpenseUser> findByExpense(Expense expense);

    List<ExpenseUser> findByUserId(long userId);
    @Query("""
            SELECT eu
            FROM ExpenseUser eu
            WHERE eu.user.id = :userId
            AND eu.expense.id NOT IN (
                SELECT ge.expense.id
                FROM GroupExpense ge
            )
            """)
    List<ExpenseUser> findNonGroupExpensesByUserId(@Param("userId") long userId);
}