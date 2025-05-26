package com.santosh.bankingsystem.userstatement.service.impl;

import com.santosh.bankingsystem.repository.AccountRepository;
import com.santosh.bankingsystem.userstatement.service.StatementService;
import com.santosh.bankingsystem.usertransaction.dto.TransactionResponseDTO;
import com.santosh.bankingsystem.usertransaction.entity.Transaction;
import com.santosh.bankingsystem.usertransaction.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatementServiceImpl implements StatementService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<TransactionResponseDTO> getAllStatements(Long accountId) {
        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<TransactionResponseDTO> getStatementsByDateRange(Long accountId, LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByAccountIdAndTimestampBetween(accountId, start, end)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<TransactionResponseDTO> getStatementsByType(Long accountId, String type) {
        return transactionRepository.findByAccountIdAndTransactionType(accountId, type.toUpperCase())
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public boolean writeStatementToCsv(Long accountId, HttpServletResponse response) throws IOException {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        if (transactions.isEmpty()) {
            return false;
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=statement.csv");

        PrintWriter writer = response.getWriter();
        writer.println("TransactionId,AccountId,Type,Amount,Date,Description");

        for (Transaction txn : transactions) {
            writer.printf("%d,%d,%s,%.2f,%s%n",
                    txn.getId(),
                    txn.getAccount().getId(),
                    txn.getTransactionType(),
                    txn.getAmount(),
                   // txn.getTransactionDate(),
                    txn.getDescription()
            );
        }

        writer.flush();
        return true;
    }

    private TransactionResponseDTO convertToDTO(Transaction txn) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(txn.getId());
        dto.setTransactionType(txn.getTransactionType());
        dto.setAmount(txn.getAmount());
        dto.setDescription(txn.getDescription());
        dto.setTimestamp(txn.getTimestamp());
        dto.setAccountId(txn.getAccount().getId());
        return dto;
    }
}