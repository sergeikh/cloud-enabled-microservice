package com.example.task.service.data.repository;

import com.example.task.service.configuration.JpaConfiguration;
import com.example.task.service.data.BaseTestClassForRepository;
import com.example.task.service.data.model.Task;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfiguration.class)
public class TaskRepositoryTestIT extends BaseTestClassForRepository {

    @Test
    @Transactional
    public void shouldCorrectlyFindAllTasks() {
        Iterable<Task> tasksFromDB = taskRepository.findAll();

        assertEquals(taskRepository.count(), 3);
        assertThat(tasksFromDB, IsCollectionContaining.hasItem(task1User1));
    }

    @Test
    @Transactional
    public void shouldFindAllByUser() {
        List<Task> user1Tasks = taskRepository.findAllByUser(1l);
        assertEquals(user1Tasks.size(), 2);

        List<Task> user2Tasks = taskRepository.findAllByUser(2l);
        assertEquals(user2Tasks.size(), 1);
    }

    @Test
    @Transactional
    public void shouldFindAllByUserAndCompletedIsTrue() {
        List<Task> user1CompletedTasks = taskRepository.findAllByUserAndCompleteIsTrue(1l);
        assertEquals(user1CompletedTasks.size(), 1);

        List<Task> user2CompletedTasks = taskRepository.findAllByUserAndCompleteIsTrue(2L);
        assertEquals(user2CompletedTasks.size(), 0);
    }

    @Test
    @Transactional
    public void shouldFindAllByCompleteIsTrue() {

        List<Task> completedTasks = taskRepository.findAllByCompleteIsTrue();
        assertEquals(completedTasks.size(), 1);
    }
}