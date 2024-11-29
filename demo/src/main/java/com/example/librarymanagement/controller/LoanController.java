package com.example.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.model.Loan;
import com.example.librarymanagement.model.User;
import com.example.librarymanagement.service.BookService;
import com.example.librarymanagement.service.LoanService;
import com.example.librarymanagement.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public String getAllLoans(Model model) {
        List<Loan> loans = loanService.getAllLoans();
        model.addAttribute("loans", loans);
        return "loans";
    }

    @GetMapping("/new")
    public String createLoanForm(Model model) {
        model.addAttribute("loan", new Loan());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("books", bookService.getAllBooks());
        return "create_loan";
    }

    @PostMapping
    public String saveLoan(@ModelAttribute Loan loan) {
        loanService.saveLoan(loan);
        return "redirect:/loans";
    }

    @GetMapping("/{id}")
    public String getLoanById(@PathVariable Long id, Model model) {
        Optional<Loan> loan = loanService.getLoanById(id);
        model.addAttribute("loan", loan.orElse(null));
        return "view_loan";
    }

    @GetMapping("/edit/{id}")
    public String editLoanForm(@PathVariable Long id, Model model) {
        Optional<Loan> loan = loanService.getLoanById(id);
        model.addAttribute("loan", loan.orElse(null));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("books", bookService.getAllBooks());
        return "edit_loan";
    }

    @PostMapping("/update/{id}")
    public String updateLoan(@PathVariable Long id, @ModelAttribute Loan loan) {
        loanService.updateLoan(id, loan);
        return "redirect:/loans";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return "redirect:/loans";
    }
}
