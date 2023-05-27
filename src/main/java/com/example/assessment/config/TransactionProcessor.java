package com.example.assessment.config;

import com.example.assessment.entity.Transaction;
import org.springframework.batch.item.ItemProcessor;

public class TransactionProcessor implements ItemProcessor<Transaction,Transaction> {

    @Override
    public Transaction process(Transaction transaction) throws Exception {
        return transaction;
    }
}