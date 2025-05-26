package com.santosh.bankingsystem.userstatement.controller;

import com.santosh.bankingsystem.dto.ApiResponse;
import com.santosh.bankingsystem.exceptions.ResourceNotFoundException;
import com.santosh.bankingsystem.userstatement.service.StatementService;
import com.santosh.bankingsystem.usertransaction.dto.TransactionResponseDTO;
import com.santosh.bankingsystem.usertransaction.entity.Transaction;
import com.santosh.bankingsystem.usertransaction.service.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/statements")
public class StatementController {

    @Autowired
    private StatementService statementService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getAllStatementsByAccount(@PathVariable Long accountId) {

        List<TransactionResponseDTO> transactions = transactionService.getTransactionsByAccountId(accountId);
        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("No statements found for account ID: " + accountId);
        }

        ApiResponse<List<TransactionResponseDTO>> response = new ApiResponse<>(
                true, "Statements fetched successfully", transactions,null
        );
         return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountId}/date-range")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getStatementsByDateRange(
            @PathVariable Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
      //  return ResponseEntity.ok(statementService.getStatementsByDateRange(accountId, start, end));
        return ResponseEntity.ok(new ApiResponse<>(true,"Statement fetched successfully",statementService.getStatementsByDateRange(accountId, start, end),null));
    }

    @GetMapping("/download/{accountId}")
    public ResponseEntity<?> downloadTransactionStatement(@PathVariable Long accountId,
                                             HttpServletResponse response) throws IOException {
        boolean downloaded = statementService.writeStatementToCsv(accountId, response);

        if (!downloaded) {
            ApiResponse<String> apiResponse = new ApiResponse<>(false, "No transactions found", null, "ERR_NO_TXN");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }

        return null; // File has been written to response
    }

    @GetMapping("/account/{accountId}/type")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getStatementsByType(
            @PathVariable Long accountId,
            @RequestParam String type) {
       // return ResponseEntity.ok(statementService.getStatementsByType(accountId, type));
        return ResponseEntity.ok(new ApiResponse<>(true,"Statement Fetched Successfully",statementService.getStatementsByType(accountId, type),null));
    }
}