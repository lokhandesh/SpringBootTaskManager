package com.santosh.bankingsystem.usertransaction.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {
    private Long id;
    private String transactionType;
    private BigDecimal amount;
    private String description;
    private LocalDateTime timestamp;
    private Long accountId;
}