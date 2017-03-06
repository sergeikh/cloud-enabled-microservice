package com.example.task.service.v1.boundary;

import com.example.task.service.TenantExtractor;
import com.example.task.service.data.model.Task;
import com.example.task.service.data.repository.TaskRepository;
import com.example.task.service.service.TaskService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/tasks")
@Log
public class TasksController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskService taskService;
    @Autowired
    TenantExtractor tenantExtractor;


    @RequestMapping(value="", method= RequestMethod.GET)
    @ApiOperation(value = "Returns all Tasks, pageable.", response = Task.class, responseContainer = "List",
            notes = "Return pageable list of tasks, http://localhost:8080/v1/tasks?page=0&size=2" +
                    "First page starts from 0, default page size is 20.")
    public ResponseEntity<Page<Task>> getAllTasks(Pageable pageable) {
        log.info("GET Tasks all");

        return new ResponseEntity<>(taskRepository.findAll(pageable), HttpStatus.OK);
    }

    /**
     * Only POST of new Task is require authentication with OAuth2 service
     * Tenant id is decoded from JWT Token in Authorization header
     * (for demo, security is configured in TaskServiceApplication.java)
     */
    @RequestMapping(value="", method= RequestMethod.POST)
    @ApiOperation(value = "Creates a new Task", response = Void.class,
            notes = "New task URL will be send in the location response Header. Created, Id will be set to Null.")
    public ResponseEntity<?> createTask(@Valid @RequestBody Task newTask, @RequestHeader("Authorization") String authorizationToken) {

        // decode tenant
        log.info(String.format("Creating task for Tenant id=%s", tenantExtractor.decodeTenantId(authorizationToken)));

        Task createdTask = taskService.createTask(newTask);

        //	Set	the	location header	for	the	newly created resource
        HttpHeaders responseHeaders	= new HttpHeaders();
        URI newTaskUri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(createdTask.getId()).toUri();
        responseHeaders.setLocation(newTaskUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{taskId}", method= RequestMethod.GET)
    @ApiOperation(value = "Retrieves a Task associated with the given taskId", response = Task.class)
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        log.info("GET Task id " + taskId);

        taskService.verifyTaskId(taskId);
        Task taskById = taskRepository.findOne(taskId);

        return new ResponseEntity<>(taskById, HttpStatus.OK);
    }

    @RequestMapping(value="/{taskID}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTask(@PathVariable Long taskID, @RequestBody Task task) {
        log.info(String.format("Updating task with id=%d on=%s", taskID, task.toString()));
        Task fromDB = taskRepository.findOne(taskID);

        fromDB.setName(task.getName());
        fromDB.setDescription(task.getDescription());

        taskRepository.save(fromDB);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{taskId}/assign/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        log.info("POST Assign task " + taskId + " to user " + userId);

        taskService.assign(taskId, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/complete/{taskId}", method = RequestMethod.POST)
    public ResponseEntity<?> completeTask(@PathVariable Long taskId) {
        log.info("POST complete task " + taskId);

        taskService.complete(taskId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/assigned/{userId}",	method= RequestMethod.GET)
    public ResponseEntity<Iterable<Task>> getAllUserTasks(@PathVariable Long userId) {
        log.info("GET user tasks " + userId);
        taskService.verifyUserId(userId);

        return new ResponseEntity<>(taskRepository.findAllByUser(userId), HttpStatus.OK);
    }

    @RequestMapping(value="/assigned/{userId}/complete",	method= RequestMethod.GET)
    public ResponseEntity<Iterable<Task>> getCompleteUserTasks(@PathVariable Long userId) {
        log.info("GET complete user tasks " + userId);
        taskService.verifyUserId(userId);

        return new ResponseEntity<>(taskRepository.findAllByUserAndCompleteIsTrue(userId), HttpStatus.OK);
    }

    @RequestMapping(value="/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        log.info("Delete Task " + taskId);

        taskService.delete(taskId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
