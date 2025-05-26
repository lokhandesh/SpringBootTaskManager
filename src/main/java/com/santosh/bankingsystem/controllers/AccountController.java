package com.santosh.bankingsystem.controllers;

import com.santosh.bankingsystem.dto.ApiResponse;
import com.santosh.bankingsystem.entity.AccountRequestDTO;
import com.santosh.bankingsystem.entity.AccountResponseDTO;
import com.santosh.bankingsystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Change: userId from request body
    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponseDTO>> createAccount(@RequestBody AccountRequestDTO dto) {
       return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"Accounts created successfully",accountService.createAccount(dto.getUserId(), dto),null));
      //  return new ResponseEntity<>(accountService.createAccount(dto.getUserId(), dto), HttpStatus.CREATED);
    }

    /*// Optional: get all accounts (across users)
    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }*/

    // Keep this for getting accounts by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AccountResponseDTO>>> getAccounts(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(true,"Accounts fetched successfully",accountService.getAccountsByUserId(userId),null));
    }

    // Get account by accountId
    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getAccountById(@PathVariable Long accountId) {
        return ResponseEntity.ok(new ApiResponse<>(true,"Accounts fetched successfully",accountService.getAccountById(accountId),null));
    }

    @GetMapping("/by-account-number/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(new ApiResponse<>(true,"Accounts fetched successfully",accountService.getByAccountNumber(accountNumber),null));
    }

    // Delete account by accountId
    @DeleteMapping("/{accountId}")
    public ResponseEntity<ApiResponse<Object>> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccountById(accountId);
       // return ResponseEntity.noContent().build();
        return ResponseEntity.ok(new ApiResponse<>(true,"Account deleted successfully",null,null));
    }
}