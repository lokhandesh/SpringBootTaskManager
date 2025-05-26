package com.santosh.bankingsystem.controllers;

import com.santosh.bankingsystem.dto.ApiResponse;
import com.santosh.bankingsystem.entity.UserRequestDTO;
import com.santosh.bankingsystem.entity.UserResponseDTO;
import com.santosh.bankingsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@RequestBody @Valid UserRequestDTO dto) {
        UserResponseDTO user = userService.createUser(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "User created successfully", user,null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> response = userService.getAllUsers();
      //  return ResponseEntity.status(HttpStatus.)
        return ResponseEntity.ok(new ApiResponse<>(true, "Users fetched successfully", response,null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {
        UserResponseDTO responseDTO = userService.getUserById(id);
       // return ResponseEntity.ok(userService.getUserById(id));
        return ResponseEntity.ok(new ApiResponse<>(true,"User fetched successfully",responseDTO,null));
    }
}