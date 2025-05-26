package com.santosh.bankingsystem.exceptions;

import com.santosh.bankingsystem.dto.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();

        // Skip wrapping Swagger-related paths
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return clean 500
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage(), null, "ERR_404"));
    }

    // 1. Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, ex.getMessage(), null, "ERR_404"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, ex.getMessage(), null, "ERR_401"));
    }

    // 2. Validation Errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(new ApiResponse<>(false, "Validation failed", errors, "ERR_404"), HttpStatus.BAD_REQUEST);
    }

    // 3. Malformed JSON Request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleMalformedJson(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, "Malformed JSON request", null, "ERR_404"), HttpStatus.BAD_REQUEST);
    }

    // 4. JWT Expired or Invalid
    @ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class})
    public ResponseEntity<ApiResponse<Object>> handleJwtErrors(RuntimeException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, "Invalid or expired JWT token", null, "ERR_403"), HttpStatus.UNAUTHORIZED);
    }

    // 5. Access Denied
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, "Access denied", null, "ERR_403"), HttpStatus.FORBIDDEN);
    }

    // 6. Username not found (during login)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUsernameNotFound(UsernameNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, "Username not found", null, "ERR_401"), HttpStatus.UNAUTHORIZED);
    }

   /* // 7. General Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex) {
        ex.printStackTrace(); // For debugging; remove in prod
        return new ResponseEntity<>(new ApiResponse<>(false, "Internal server error", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

}