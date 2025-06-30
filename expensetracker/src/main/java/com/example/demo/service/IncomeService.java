package com.example.demo.service;
import com.example.demo.model.Income;
import com.example.demo.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;

    // Add new income
    public Income addIncome(Income income) {
        return incomeRepository.save(income);
    }

    // Get all incomes sorted by most recent
    public List<Income> getAllIncomes() {
        return incomeRepository.findAllByOrderByDateDesc();
    }

    // Get income by ID (optional use case)
    public Optional<Income> getIncomeById(Long id) {
        return incomeRepository.findById(id);
    }

    // Delete income by ID
    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    // Calculate total income
    public double getTotalIncome() {
        return incomeRepository.findAll()
                .stream()
                .mapToDouble(Income::getAmount)
                .sum();
    }
// Update income by ID
public Income updateIncome(Long id, Income updatedIncome) {
    return incomeRepository.findById(id).map(income -> {
        income.setTitle(updatedIncome.getTitle());
        income.setAmount(updatedIncome.getAmount());
        income.setDate(updatedIncome.getDate());
        income.setCategory(updatedIncome.getCategory());
        income.setDescription(updatedIncome.getDescription());
        return incomeRepository.save(income);
    }).orElseThrow(() -> new RuntimeException("Income not found with ID: " + id));
}

    // Get latest n income transactions (e.g., 3)
    public List<Income> getLatestIncomes(int limit) {
        List<Income> incomes = incomeRepository.findAllByOrderByDateDesc();
        return incomes.subList(0, Math.min(limit, incomes.size()));
    }
}