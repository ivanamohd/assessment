package com.example.assessment.controller;

import com.example.assessment.ResourceNotFoundException;
import com.example.assessment.entity.Transaction;
import com.example.assessment.repository.TransactionRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction newTransaction, @PathVariable Long id) {
        try {
            Transaction existingTransaction = transactionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

            if (existingTransaction.getVersion() == (newTransaction.getVersion())) {
                existingTransaction.setDESCRIPTION(newTransaction.getDESCRIPTION());
                existingTransaction.setVersion(existingTransaction.getVersion() + 1);

                // Save updated transaction
                Transaction updatedTransaction = transactionRepository.save(existingTransaction);
                return ResponseEntity.ok(updatedTransaction);
            } else {
                // Handle concurrent update conflict
                throw new OptimisticLockException("Transaction has been modified by another user. Please try again.");
            }
        } catch (OptimisticLockException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}
