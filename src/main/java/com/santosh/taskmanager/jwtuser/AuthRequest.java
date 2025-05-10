package com.santosh.taskmanager.jwtuser;

public class AuthRequest {
    private String username;
    private String password;

    // Getters and Setters
    // Add constructor if needed
    public AuthRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}