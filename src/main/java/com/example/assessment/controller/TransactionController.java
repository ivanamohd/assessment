package com.example.assessment.controller;

import com.example.assessment.entity.Transaction;
import com.example.assessment.repository.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Get all transactions with search
    @GetMapping("/transactions")
    Page<Transaction> all(@RequestParam(value = "DESCRIPTION", required = false) String DESCRIPTION,
                          @RequestParam(value = "CUSTOMER_ID", required = false) String CUSTOMER_ID,
                          @RequestParam(value = "ACCOUNT_NUMBER", required = false) String ACCOUNT_NUMBER,
                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (DESCRIPTION != null) {
            // Filter transactions by description
            return transactionRepository.findByDESCRIPTION(DESCRIPTION, pageable);
        } else if (CUSTOMER_ID != null) {
            // Filter transactions by customer ID
            return transactionRepository.findByCUSTOMER_ID(CUSTOMER_ID, pageable);
        } else if (ACCOUNT_NUMBER != null) {
            // Filter transactions by account number
            return transactionRepository.findByACCOUNT_NUMBER(ACCOUNT_NUMBER, pageable);
        } else {
            // Return all transactions
            return transactionRepository.findAll(pageable);
        }
    }

    // Get single transaction
    @GetMapping("/transactions/{id}")
    Transaction getSingleTransaction(@PathVariable Long id) {

        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found with ID: " + id));
    }

    // Update transaction's description
    @PutMapping("/transactions/{id}")
    Transaction updateTransaction(@RequestBody Transaction newTransaction, @PathVariable Long id) {

        return transactionRepository.findById(id)
                .map(existingTransaction -> {
                    existingTransaction.setDESCRIPTION(newTransaction.getDESCRIPTION());
                    return transactionRepository.save(existingTransaction);
                })
                .orElseGet(() -> {
                    newTransaction.setID(id);
                    return transactionRepository.save(newTransaction);
                });
    }
}
