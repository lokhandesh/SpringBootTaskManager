package com.santosh.taskmanager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestDTO {

    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private String status;
    private String priority;
    private Long userId;

}
