package com.santosh.taskmanager.controller;

import com.santosh.taskmanager.dto.TaskRequestDTO;
import com.santosh.taskmanager.dto.TaskResponseDTO;
import com.santosh.taskmanager.mapper.TaskMapper;
import com.santosh.taskmanager.model.Task;
import com.santosh.taskmanager.repository.TaskRepository;
import com.santosh.taskmanager.service.TaskService;
import com.santosh.taskmanager.util.TaskSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Operation(summary = "To create the Task")
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskRequestDTO dto) {
        return new ResponseEntity<>(taskService.createTask(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "To retrive all the Task")
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Operation(summary = "To get the Task by Id")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @Operation(summary = "To update the Task")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO dto) {
        return ResponseEntity.ok(taskService.updateTask(id, dto));
    }

    @Operation(summary = "To delete the Task")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(taskService.getTasksPage(page, size));
    }

    @GetMapping("/filtered")
    public ResponseEntity<Page<TaskResponseDTO>> getFilteredTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<Task> taskPage = taskService.getFilteredTasks(status, priority, page, size, sortBy);
        Page<TaskResponseDTO> response = taskPage.map(taskMapper::convertToDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByUserId(userId));
    }



}
