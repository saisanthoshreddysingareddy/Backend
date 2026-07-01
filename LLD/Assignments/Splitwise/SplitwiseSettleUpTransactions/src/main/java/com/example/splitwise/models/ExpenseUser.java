package com.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ExpenseUser extends BaseModel {

    @ManyToOne
    private Expense expense;

    @ManyToOne
    private User user;

    private double amount;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;
}