package com.example.task.service.service;

import com.example.task.service.data.model.Task;
import com.example.task.service.data.repository.TaskRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

@Service
@Log
public class ServiceInitializer {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void onInit() {
        if(taskRepository.count() != 0) {
            log.info("Data is present. No data loading is performed.");

            return;
        }

        resetIdAutoincrement();
        initTasks();

        log.info("Initialization complete.");
    }

    /**
     * For integration test.
     */
    private void resetIdAutoincrement() {
        try {
            dataSource.getConnection().createStatement().execute("ALTER TABLE Task ALTER COLUMN ID RESTART WITH 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initTasks() {
        Task task1 = new Task("Task 1", "Task 1 description", 1l);

        Task task2 = new Task("Task 2", "Task 2 description", 1l);
        task2.setComplete(true);

        Task task3 = new Task("Task 3", "Task 3 description", 2l);

        taskRepository.save(Arrays.asList(task1, task2, task3));

        log.info("Tasks are loaded");
    }
}
