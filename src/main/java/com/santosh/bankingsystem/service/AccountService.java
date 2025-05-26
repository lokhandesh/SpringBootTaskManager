package com.santosh.bankingsystem.service;

import com.santosh.bankingsystem.entity.AccountRequestDTO;
import com.santosh.bankingsystem.entity.AccountResponseDTO;

import java.util.List;

public interface AccountService {
    AccountResponseDTO createAccount(Long userId, AccountRequestDTO dto);
    List<AccountResponseDTO> getAccountsByUserId(Long userId);
   // List<AccountResponseDTO> getAllAccounts();
    AccountResponseDTO getAccountById(Long accountId);
    AccountResponseDTO getByAccountNumber(String accountNumber);
    void deleteAccountById(Long accountId);

}