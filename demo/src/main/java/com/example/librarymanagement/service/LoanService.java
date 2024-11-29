package com.example.librarymanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.model.Loan;
import com.example.librarymanagement.repository.LoanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    // Retrieve all loans
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // Save a new loan
    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    // Find a loan by ID
    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    // Update an existing loan
    public Loan updateLoan(Long id, Loan updatedLoan) {
        return loanRepository.findById(id).map(loan -> {
            loan.setLoanDate(updatedLoan.getLoanDate());
            loan.setReturnDate(updatedLoan.getReturnDate());
            loan.setUser(updatedLoan.getUser());
            loan.setBook(updatedLoan.getBook());
            return loanRepository.save(loan);
        }).orElseThrow(() -> new RuntimeException("Loan not found with id " + id));
    }

    // Delete a loan by ID
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
