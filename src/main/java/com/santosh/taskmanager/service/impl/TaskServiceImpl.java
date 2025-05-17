package com.santosh.taskmanager.service.impl;

import com.santosh.taskmanager.dto.TaskRequestDTO;
import com.santosh.taskmanager.dto.TaskResponseDTO;
import com.santosh.taskmanager.exception.TaskNotFoundException;
import com.santosh.taskmanager.jwtuser.AppUser;
import com.santosh.taskmanager.jwtuser.UserRepository;
import com.santosh.taskmanager.mapper.TaskMapper;
import com.santosh.taskmanager.model.Task;
import com.santosh.taskmanager.repository.TaskRepository;
import com.santosh.taskmanager.service.TaskService;
import com.santosh.taskmanager.util.TaskSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        // Get currently authenticated username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch user from DB
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setCompleted(dto.isCompleted());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return taskMapper.convertToDto(savedTask);
    }

    @Override
    public TaskResponseDTO getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return taskMapper.convertToDto(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        // Update fields from DTO
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setCompleted(dto.isCompleted());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());

        Task updated = taskRepository.save(task);
        return taskMapper.convertToDto(updated);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    @Override
    public Page<TaskResponseDTO> getTasksPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return taskRepository.findAll(pageRequest)
                .map(this::convertToDto);
    }

    private TaskResponseDTO convertToDto(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        return dto;
    }

    @Override
    public Page<Task> getFilteredTasks(String status, String priority, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Specification<Task> spec = Specification
                .where(TaskSpecification.hasStatus(status))
                .and(TaskSpecification.hasPriority(priority));

        return taskRepository.findAll(spec, pageable);
    }
    @Override
    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(taskMapper::convertToDto)
                .collect(Collectors.toList());
    }
}