package com.santosh.bankingsystem.userservice;

import com.santosh.bankingsystem.userservice.dto.UserRegisterRequestDTO;
import com.santosh.bankingsystem.userservice.dto.UserRegisteredResponseDTO;

public interface AuthService {
    UserRegisteredResponseDTO register(UserRegisterRequestDTO dto);
}