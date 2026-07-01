package com.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Expense extends BaseModel {

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addedAt;

    private String description;

    private String proofUrl;

    @OneToMany(mappedBy = "expense",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<ExpenseUser> expenseUsers;
}