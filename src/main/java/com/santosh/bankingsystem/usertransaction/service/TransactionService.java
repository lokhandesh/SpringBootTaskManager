package com.santosh.bankingsystem.usertransaction.service;

import com.santosh.bankingsystem.usertransaction.dto.TransactionRequestDTO;
import com.santosh.bankingsystem.usertransaction.dto.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {
    TransactionResponseDTO transferFunds(TransactionRequestDTO dto);
    List<TransactionResponseDTO> getTransactionsByAccountId(Long accountId);
    TransactionResponseDTO deposit(TransactionRequestDTO dto);
    TransactionResponseDTO withdraw(TransactionRequestDTO dto);
}