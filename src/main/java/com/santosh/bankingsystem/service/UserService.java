package com.santosh.bankingsystem.service;

import com.santosh.bankingsystem.entity.UserRequestDTO;
import com.santosh.bankingsystem.entity.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO dto);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
}