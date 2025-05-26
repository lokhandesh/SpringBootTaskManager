package com.santosh.bankingsystem.userservice.impl;

import com.santosh.bankingsystem.entity.User;
import com.santosh.bankingsystem.repository.UserRepository;
import com.santosh.bankingsystem.userservice.AuthService;
import com.santosh.bankingsystem.userservice.dto.UserRegisterRequestDTO;
import com.santosh.bankingsystem.userservice.dto.UserRegisteredResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserRegisteredResponseDTO register(UserRegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);
        return new UserRegisteredResponseDTO(savedUser.getId(), savedUser.getEmail(),savedUser.getUsername());
    }
}
