package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Income;
import com.example.demo.repository.IncomeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;

    // ✅ Get income by ID
    @GetMapping("/incomes/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable("id") Long id) {
        Optional<Income> incomeData = incomeRepository.findById(id);

        if (incomeData.isPresent()) {
            return new ResponseEntity<>(incomeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Get all incomes
    @GetMapping("/incomes")
    public ResponseEntity<List<Income>> getAllIncomes() {
        try {
            List<Income> incomes = new ArrayList<>();
            incomeRepository.findAll().forEach(incomes::add);

            return new ResponseEntity<>(incomes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Create income
    @PostMapping("/incomes")
    public ResponseEntity<Income> createIncome(@RequestBody Income income) {
        try {
            Income _income = incomeRepository.save(new Income(
                income.getTitle(),
                income.getAmount(),
                income.getCategory(),
                income.getDescription(),
                income.getDate(),
                income.getType()
            ));
            return new ResponseEntity<>(_income, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Update income
    @PutMapping("/incomes/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable("id") Long id, @RequestBody Income income) {
        Optional<Income> incomeData = incomeRepository.findById(id);

        if (incomeData.isPresent()) {
            Income _income = incomeData.get();
            _income.setTitle(income.getTitle());
            _income.setAmount(income.getAmount());
            _income.setCategory(income.getCategory());
            _income.setDescription(income.getDescription());
            _income.setDate(income.getDate());
            _income.setType(income.getType());

            return new ResponseEntity<>(incomeRepository.save(_income), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Delete income by ID
    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<HttpStatus> deleteIncome(@PathVariable("id") Long id) {
        try {
            incomeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Delete all incomes
    @DeleteMapping("/incomes")
    public ResponseEntity<HttpStatus> deleteAllIncomes() {
        try {
            incomeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
