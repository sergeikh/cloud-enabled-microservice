package com.example.task.service.service;

import com.example.task.service.data.model.Task;
import com.example.task.service.data.repository.TaskRepository;
import com.example.task.service.exception.TaskNotFoundException;
import com.example.task.service.exception.UserNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.Objects.isNull;

@Service
@Log
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    /**
     * Create new Task.
     * newTask.id should be null.
     */
    public Task createTask(Task newTask) {
        newTask.setId(null);
        newTask.setCreated(null);

        log.info("Task created " + newTask.getName());

        return taskRepository.save(newTask);
    }

    /**
     * Update task: name, description, etc.
     */
    public Task updateTask(Task update) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Assign task taskId, to a user userId.
     */
    public Task assign(Long taskId, Long userId) {
        verifyTaskId(taskId);
        verifyUserId(userId);

        Task fromDB = taskRepository.findOne(taskId);
        fromDB.setUser(userId);
        fromDB.setAssigned(new Date());

        log.info("Task " + taskId + " assigned to user " + userId);
        return taskRepository.save(fromDB);
    }

    /**
     * Set Task as completed.
     */
    public Task complete(Long taskId) {
        verifyTaskId(taskId);

        Task fromDB = taskRepository.findOne(taskId);
        fromDB.setComplete(true);
        fromDB.setCompleted(new Date());

        log.info("Task completed " + taskId);

        return taskRepository.save(fromDB);
    }

    /**
     * Delete task.
     */
    public void delete(Long taskId) {
        verifyTaskId(taskId);
        taskRepository.delete(taskId);

        log.info("Task deleted " + taskId);
    }

    public void verifyTaskId(Long taskId) throws TaskNotFoundException {
        Task fromDB = taskRepository.findOne(taskId);

        if (isNull(fromDB))
            throw new TaskNotFoundException("Task with id " + taskId + " is not found.");
    }

    public void verifyUserId(Long userId) {
        if (isNull(userId))
            throw new UserNotFoundException("User id can't be null.");
    }
}
