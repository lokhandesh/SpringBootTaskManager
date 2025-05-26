package com.santosh.bankingsystem.service.impl;

import com.santosh.bankingsystem.entity.Account;
import com.santosh.bankingsystem.entity.AccountRequestDTO;
import com.santosh.bankingsystem.entity.AccountResponseDTO;
import com.santosh.bankingsystem.entity.User;
import com.santosh.bankingsystem.repository.AccountRepository;
import com.santosh.bankingsystem.repository.UserRepository;
import com.santosh.bankingsystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AccountResponseDTO createAccount(Long userId, AccountRequestDTO dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setBalance(dto.getBalance());
        account.setCreatedAt(LocalDateTime.now());
        account.setAccountNumber(dto.getAccountNumber());
        account.setUser(user);

        Account saved = accountRepository.save(account);

        AccountResponseDTO response = new AccountResponseDTO();
        response.setId(saved.getId());
        response.setBalance(saved.getBalance());
        response.setAccountNumber(saved.getAccountNumber());
       // response.setCreatedAt(saved.getCreatedAt());
        response.setUserId(user.getId());

        return response;
    }

    @Override
    public List<AccountResponseDTO> getAccountsByUserId(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AccountResponseDTO getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));
        return mapToDTO(account);
    }

    @Override
    public AccountResponseDTO getByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return mapToDTO(account);
    }

    @Override
    public void deleteAccountById(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found with ID: " + accountId);
        }
        accountRepository.deleteById(accountId);
    }

    /*@Override
    public List<AccountResponseDTO> getAllAccounts() {
      return  accountRepository.findAll().stream().map(account -> {
            AccountResponseDTO dto = new AccountResponseDTO();
            dto.setId(account.getId());
            dto.setBalance(account.getBalance());
            dto.setAccountNumber(account.getAccountNumber());
           // dto.setUserId(account.getUser());
            return dto;
        }).collect(Collectors.toList());
       // return List.of();
    }*/

    private AccountResponseDTO mapToDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setUserId(account.getUser().getId());
        return dto;
    }

}