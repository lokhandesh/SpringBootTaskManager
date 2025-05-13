package com.santosh.taskmanager.repository;

import com.santosh.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {
}
