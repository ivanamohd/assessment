package com.example.assessment.repository;

import com.example.assessment.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE t.DESCRIPTION = :description")
    Page<Transaction> findByDESCRIPTION(String description, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.ACCOUNT_NUMBER = :accountNumber")
    Page<Transaction> findByACCOUNT_NUMBER(String accountNumber, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.CUSTOMER_ID = :customerId")
    Page<Transaction> findByCUSTOMER_ID(String customerId, Pageable pageable);
}
