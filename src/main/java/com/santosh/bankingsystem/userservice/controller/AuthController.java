package com.santosh.bankingsystem.userservice.controller;

import com.santosh.bankingsystem.dto.ApiResponse;
import com.santosh.bankingsystem.security.JwtUtil;
import com.santosh.bankingsystem.userservice.AuthService;
import com.santosh.bankingsystem.userservice.dto.AuthRequestDTO;
import com.santosh.bankingsystem.userservice.dto.AuthResponseDTO;
import com.santosh.bankingsystem.userservice.dto.UserRegisterRequestDTO;
import com.santosh.bankingsystem.userservice.dto.UserRegisteredResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegisteredResponseDTO>> register(@Valid @RequestBody UserRegisterRequestDTO dto) {

        return ResponseEntity.ok(new ApiResponse<>(true,"Registered Successfully",authService.register(dto),null));
      //  return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO request) {
        // Authenticate user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Load user and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthResponseDTO(token);
    }

    /*@PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody AuthRequestDTO dto) {
        String token = jwtService.generateToken(dto.getEmail(), dto.getPassword());
        Map<String, String> responseMap = Map.of("token", token);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", responseMap));
    }*/

}