package com.example.assessment.repository;

import com.example.assessment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE t.DESCRIPTION = :description")
    List<Transaction> findByDESCRIPTION(String description);
    @Query("SELECT t FROM Transaction t WHERE t.ACCOUNT_NUMBER = :accountNumber")
    List<Transaction> findByACCOUNT_NUMBER(String accountNumber);

    @Query("SELECT t FROM Transaction t WHERE t.CUSTOMER_ID = :customerId")
    List<Transaction> findByCUSTOMER_ID(String customerId);
}
