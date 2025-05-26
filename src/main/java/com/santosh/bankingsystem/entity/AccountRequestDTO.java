package com.santosh.bankingsystem.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDTO {
    private BigDecimal balance;
    private Long userId;
    private String accountNumber;
}