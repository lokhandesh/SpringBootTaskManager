package com.santosh.bankingsystem.usertransaction.controller;

import com.santosh.bankingsystem.dto.ApiResponse;
import com.santosh.bankingsystem.usertransaction.dto.TransactionRequestDTO;
import com.santosh.bankingsystem.usertransaction.dto.TransactionResponseDTO;
import com.santosh.bankingsystem.usertransaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> transferFunds(@RequestBody TransactionRequestDTO dto) {
       // return ResponseEntity.ok("Transfer successful");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"Transfer completed successfully",transactionService.transferFunds(dto),null));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getTransactions(@PathVariable Long accountId) {
        //return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
        return ResponseEntity.ok(new ApiResponse<>(true,"Transaction fetched successfully",transactionService.getTransactionsByAccountId(accountId),null));
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> deposit(@RequestBody TransactionRequestDTO dto) {
       // return ResponseEntity.ok(transactionService.deposit(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"Deposit successful",transactionService.deposit(dto),null));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> withdraw(@RequestBody TransactionRequestDTO dto) {
        //return ResponseEntity.ok(transactionService.withdraw(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"Withdrawl successful",transactionService.withdraw(dto),null));
    }
}