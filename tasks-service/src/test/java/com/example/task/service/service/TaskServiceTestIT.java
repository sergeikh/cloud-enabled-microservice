package com.example.task.service.service;

import com.example.task.service.data.model.Task;
import com.example.task.service.data.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTestIT {
    TaskService cut;
    Task beforeSave, afterSave;
    Task beforeAssign, afterAssign;
    Task beforeComplete, afterComplete;
    @Spy
    Task spy;

    @Mock
    TaskRepository taskRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        cut = new TaskService();
        cut.taskRepository = taskRepository;
    }

    @Test
    public void shouldCreateOnlyOneNewTask() {
        beforeSave = new Task();
        beforeSave.setName("Name");
        beforeSave.setDescription("Description");

        afterSave = new Task();
        afterSave.setName("Name");
        afterSave.setName("Description");
        afterSave.setId(1l);
        afterSave.setCreated(new Date());

        when(taskRepository.save(beforeSave)).thenReturn(afterSave);

        assertEquals(cut.createTask(beforeSave), afterSave);

        verify(taskRepository, only()).save(beforeSave);
    }

    @Test
    public void shouldCorrectlyAssignTaskToAUser() {
        beforeAssign = new Task();
        beforeAssign.setName("Name");
        beforeAssign.setId(2L);

        afterAssign = new Task();
        afterAssign.setName("Name");
        afterAssign.setId(2L);
        afterAssign.setUser(2L);

        when(taskRepository.findOne(2L)).thenReturn(beforeAssign);
        when(taskRepository.save(afterAssign)).thenReturn(afterAssign);

        assertEquals(cut.assign(2L, 2L), afterAssign);
        verify(taskRepository, times(2)).findOne(2L);
        verify(taskRepository, times(1)).save(beforeAssign);
    }

    @Test
    public void shouldMarkTaskAsCompleted() {
        beforeComplete = new Task();
        beforeComplete.setId(1L);
        beforeComplete.setName("Test");

        afterComplete = new Task();
        afterComplete.setId(1L);
        afterComplete.setName("Test");
        afterComplete.setComplete(true);
        afterComplete.setCompleted(new Date());

        when(taskRepository.findOne(1L)).thenReturn(beforeComplete);
        when(taskRepository.save(afterComplete)).thenReturn(afterComplete);

        cut.complete(1L);
        verify(taskRepository, times(2)).findOne(1L);
        verify(taskRepository, times(1)).save(beforeComplete);
    }

    @Test
    public void shouldDeleteTask() {
        when(taskRepository.findOne(1L)).thenReturn(new Task());
        cut.delete(1L);
        verify(taskRepository, times(1)).delete(1l);
    }
}