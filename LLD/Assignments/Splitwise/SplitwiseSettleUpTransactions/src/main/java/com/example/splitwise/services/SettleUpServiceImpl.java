package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.models.*;
import com.example.splitwise.repositories.ExpenseUserRepository;
import com.example.splitwise.repositories.GroupExpenseRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettleUpServiceImpl implements SettleUpService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupExpenseRepository groupExpenseRepository;

    @Autowired
    private ExpenseUserRepository expenseUserRepository;

    @Override
    public List<Transaction> settleGroup(long groupId) throws InvalidGroupException {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new InvalidGroupException("Group not found"));

        List<GroupExpense> groupExpenses =
                groupExpenseRepository.findByGroupId(group.getId());

        List<ExpenseUser> expenseUsers = new ArrayList<>();

        for (GroupExpense groupExpense : groupExpenses) {
            expenseUsers.addAll(
                    expenseUserRepository.findByExpense(groupExpense.getExpense())
            );
        }

        return settleTransactions(expenseUsers);
    }

    @Override
    public List<Transaction> settleUser(long userId) throws InvalidUserException {

        userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("User not found"));

        List<ExpenseUser> myExpenseUsers =
                expenseUserRepository.findNonGroupExpensesByUserId(userId);

        Set<Expense> uniqueExpenses = new HashSet<>();

        for (ExpenseUser expenseUser : myExpenseUsers) {
            uniqueExpenses.add(expenseUser.getExpense());
        }

        List<ExpenseUser> allExpenseUsers = new ArrayList<>();

        for (Expense expense : uniqueExpenses) {
            allExpenseUsers.addAll(
                    expenseUserRepository.findByExpense(expense)
            );
        }

        return settleTransactions(allExpenseUsers);
    }

   
    private List<Transaction> settleTransactions(List<ExpenseUser> expenseUsers) {

        Map<User, Double> balanceMap = new HashMap<>();

        for (ExpenseUser expenseUser : expenseUsers) {

            User user = expenseUser.getUser();

            balanceMap.putIfAbsent(user, 0.0);

            double balance = balanceMap.get(user);

            if (expenseUser.getExpenseType() == ExpenseType.PAID) {
                balance += expenseUser.getAmount();
            } else {
                balance -= expenseUser.getAmount();
            }

            balanceMap.put(user, balance);
        }

        // Max Heap -> Receivers
        PriorityQueue<Balance> maxHeap =
                new PriorityQueue<>(
                        (a, b) -> Double.compare(b.amount, a.amount)
                );

        PriorityQueue<Balance> minHeap =
                new PriorityQueue<>(
                        Comparator.comparingDouble(a -> a.amount)
                );

        for (Map.Entry<User, Double> entry : balanceMap.entrySet()) {

            double amount = entry.getValue();

            if (amount > 0) {
                maxHeap.offer(new Balance(entry.getKey(), amount));
            } else if (amount < 0) {
                minHeap.offer(new Balance(entry.getKey(), amount));
            }
        }

        List<Transaction> transactions = new ArrayList<>();

        while (!maxHeap.isEmpty() && !minHeap.isEmpty()) {

            Balance receiver = maxHeap.poll();
            Balance payer = minHeap.poll();

            double settledAmount =
                    Math.min(receiver.amount, -payer.amount);

            Transaction transaction = new Transaction();
            transaction.setPaidFrom(payer.user);
            transaction.setPaidTo(receiver.user);
            transaction.setAmount(settledAmount);

            transactions.add(transaction);

            receiver.amount -= settledAmount;
            payer.amount += settledAmount;

            if (receiver.amount > 0.0) {
                maxHeap.offer(receiver);
            }

            if (payer.amount < 0.0) {
                minHeap.offer(payer);
            }
        }

        return transactions;
    }

    
    private static class Balance {

        private User user;
        private double amount;

        public Balance(User user, double amount) {
            this.user = user;
            this.amount = amount;
        }
    }
}