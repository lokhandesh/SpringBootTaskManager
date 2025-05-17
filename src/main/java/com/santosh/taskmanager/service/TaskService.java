package com.santosh.taskmanager.service;

import com.santosh.taskmanager.dto.TaskRequestDTO;
import com.santosh.taskmanager.dto.TaskResponseDTO;
import com.santosh.taskmanager.model.Task;
import org.springframework.data.domain.Page;

import java.util.List;
public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO dto);
    TaskResponseDTO getTask(Long id);
    List<TaskResponseDTO> getAllTasks();
    TaskResponseDTO updateTask(Long id, TaskRequestDTO dto);
    void deleteTask(Long id);
    Page<TaskResponseDTO> getTasksPage(int page, int size);
    Page<Task> getFilteredTasks(String status, String priority, int page, int size, String sortBy);
    List<TaskResponseDTO> getTasksByUserId(Long userId);

}
