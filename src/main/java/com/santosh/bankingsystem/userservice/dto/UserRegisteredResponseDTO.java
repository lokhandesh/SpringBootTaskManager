package com.santosh.bankingsystem.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisteredResponseDTO {
    private Long id;
    private String email;
    private String username;
}