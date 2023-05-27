package com.example.assessment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRANSACTION_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long ID;
    @Column(name = "ACCOUNT_NUMBER")
    private String ACCOUNT_NUMBER;
    @Column(name = "TRX_AMOUNT")
    private String TRX_AMOUNT;
    @Column(name = "DESCRIPTION")
    private String DESCRIPTION;
    @Column(name = "TRX_DATE")
    private String TRX_DATE;
    @Column(name = "TRX_TIME")
    private String TRX_TIME;
    @Column(name = "CUSTOMER_ID")
    private String CUSTOMER_ID;


}
