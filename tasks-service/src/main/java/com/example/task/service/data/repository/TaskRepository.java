package com.example.task.service.data.repository;

import com.example.task.service.data.model.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

    List<Task> findAllByUser(Long user);

    List<Task> findAllByUserAndCompleteIsTrue(Long user);

    List<Task> findAllByCompleteIsTrue();
}
