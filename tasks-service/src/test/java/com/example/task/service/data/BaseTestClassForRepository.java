package com.example.task.service.data;

import com.example.task.service.data.model.Task;
import com.example.task.service.data.repository.TaskRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;

public class BaseTestClassForRepository {
    protected Task task1User1, task2User1Completed, task3User2;

    @Autowired
    protected TaskRepository taskRepository;

    @Before
    public void onInit() {
        task1User1 = new Task("Task 1", "Description 1", 1l);

        task2User1Completed = new Task("Task 2", "Description 2", 1l);
        task2User1Completed.setComplete(true);
        task2User1Completed.setCompleted(new Date());

        task3User2 = new Task("Task 3", "Description 3", 2l);

        taskRepository.save(Arrays.asList(task1User1, task2User1Completed, task3User2));
    }
}
