package com.santosh.bankingsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountResponseDTO {
    private Long id;
    private BigDecimal balance;
    private String accountNumber;
   // private LocalDateTime createdAt;
    private Long userId;
}