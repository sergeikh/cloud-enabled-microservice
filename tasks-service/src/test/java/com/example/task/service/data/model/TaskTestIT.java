package com.example.task.service.data.model;

import com.example.task.service.configuration.JpaConfiguration;
import com.example.task.service.data.BaseTestClassForRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfiguration.class)
public class TaskTestIT extends BaseTestClassForRepository {

    @Test
    @Transactional
    public void shouldCorrectlyPersistAllTasks() {
        Task task1FromDB = taskRepository.findOne(1L);

        assertThat(task1FromDB.getCreated(), notNullValue());
        assertEquals(task1FromDB.getName(), task1User1.getName());
        assertEquals(task1FromDB.getUser(), task1User1.getUser());

        assertThat(taskRepository.count(), is(3l));
    }
}