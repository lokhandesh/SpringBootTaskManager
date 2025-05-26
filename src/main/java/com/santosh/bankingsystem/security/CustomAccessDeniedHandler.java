package com.santosh.bankingsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santosh.bankingsystem.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ApiResponse<?> apiResponse = new ApiResponse<>(false, "Access Denied", null,"ERR_403");

        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(apiResponse);

        response.getWriter().write(responseBody);
    }
}