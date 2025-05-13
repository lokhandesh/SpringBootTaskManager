package com.santosh.taskmanager.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private String status;
    private String priority;
    private LocalDateTime createdAt;

    // Getters and setters
}