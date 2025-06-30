package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Expense;
import com.example.demo.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    // GET by ID
    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable("id") Long id) {
        Optional<Expense> expenseData = expenseRepository.findById(id);

        if (expenseData.isPresent()) {
            return new ResponseEntity<>(expenseData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET all expenses
    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        try {
            List<Expense> expenses = new ArrayList<>();
            expenseRepository.findAll().forEach(expenses::add);
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST - Create a new expense
    @PostMapping("/expenses")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        try {
            Expense _expense = expenseRepository.save(new Expense(
                expense.getTitle(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getDate(),
                expense.getType()
            ));
            return new ResponseEntity<>(_expense, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT - Update expense
    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("id") Long id, @RequestBody Expense expense) {
        Optional<Expense> expenseData = expenseRepository.findById(id);

        if (expenseData.isPresent()) {
            Expense _expense = expenseData.get();
            _expense.setTitle(expense.getTitle());
            _expense.setAmount(expense.getAmount());
            _expense.setCategory(expense.getCategory());
            _expense.setDescription(expense.getDescription());
            _expense.setDate(expense.getDate());
            _expense.setType(expense.getType());

            return new ResponseEntity<>(expenseRepository.save(_expense), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE by ID
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<HttpStatus> deleteExpense(@PathVariable("id") Long id) {
        try {
            expenseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE all
    @DeleteMapping("/expenses")
    public ResponseEntity<HttpStatus> deleteAllExpenses() {
        try {
            expenseRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
