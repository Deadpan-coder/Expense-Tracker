package com.example.demo.service;
import com.example.demo.model.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    // Add new expense
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // Get all expenses sorted by most recent
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAllByOrderByDateDesc();

    }

    // Get expense by ID (optional use case)
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    // Delete expense by ID
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    // Calculate total expenses
    public double getTotalExpenses() {
        return expenseRepository.findAll()
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    // Update expense by ID
public Expense updateExpense(Long id, Expense updatedExpense) {
    return expenseRepository.findById(id).map(expense -> {
        expense.setTitle(updatedExpense.getTitle());
        expense.setAmount(updatedExpense.getAmount());
        expense.setDate(updatedExpense.getDate());
        expense.setCategory(updatedExpense.getCategory());
        expense.setDescription(updatedExpense.getDescription());
        return expenseRepository.save(expense);
    }).orElseThrow(() -> new RuntimeException("Expense not found with ID: " + id));
}
 // Get latest n expense transactions (e.g., 3)
    public List<Expense> getLatestExpenses(int limit) {
        List<Expense> expenses = expenseRepository.findAllByOrderByDateDesc();
        return expenses.subList(0, Math.min(limit, expenses.size()));
    }
}
