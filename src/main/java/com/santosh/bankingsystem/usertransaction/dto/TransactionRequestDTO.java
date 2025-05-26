package com.santosh.bankingsystem.usertransaction.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private String description;
}