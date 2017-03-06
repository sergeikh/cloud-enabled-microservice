package com.example.task.service.v1.boundary;

import com.example.task.service.TaskServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Rest API integration test, with in memory database and data from ServiceInitializer.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TaskServiceApplication.class})
public class TasksControllerTestIT {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        mockMvc.perform(get("/v1/tasks")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(3))
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Test
    public void testCreateTask() throws Exception {
        // crate new task and save location from header
        MvcResult result = mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJzZXJnZWkiLCJzY29wZSI6WyJ3ZWJjbGllbnQiXSwidGVuYW50SWQiOiJkNDczY2YyOC0wODIxLTQ3ODYtOTM5Zi01NjI2MWQ5ZjU0NzEiLCJleHAiOjE0ODg3MjA1NjIsImF1dGhvcml0aWVzIjpbIkNSRUFURV9ORVdfVEFTSyJdLCJqdGkiOiI4Mjc0M2FmNi1jZGZkLTQ4OWEtYWU1MS03YjQzZTVhNzBhYWEiLCJjbGllbnRfaWQiOiJzYWFzX2FwcCJ9.Mv_cQC2NJryn9a7cM9GbPRIzSsExJwP7mbmzqAN4nfBvlaNp_x8NSpEo4kGgZyTDlckBo2Ee6Pa1_qjhrIjqLLKHj6lzNT7wX8Na1GT2f7tiVpXw1Nj7AAD4vmSoLfFFRQy5rBnM3assNMMKvRmXFsSPQyQVrxcNjp1omNkMNcQq1uc9zvl6ccsVkLBnIhjFHh-ZcDZIDNW1t0ZB7cdHBoonUrEQDK5cDqv_yDeFtdM1Qz8faHIByUqGPr9FoRrYLG--cxhMvjNCEV2E3tqDuNtGR1Rio-rCf05wqfQr_6XURQUzaNErtV4VuhdpfPCTiYrjrfJYOtjlorB43lgyGA")
                .content("{\"name\": \"new task\"}"))

                .andExpect(status().isCreated())
                .andExpect(header().string("Location", anything()))
                .andExpect(header().string("Location", containsString("/v1/tasks/")))
                .andReturn();

        String newTaskLocation = result.getResponse().getHeader("Location");

        // check created task
        mockMvc.perform(get(newTaskLocation)).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new task"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        // update task
        mockMvc.perform(put("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"updated\"}"))
                .andExpect(status().isOk());


        // check updated task
        mockMvc.perform(get("/v1/tasks/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updated"));
    }

    @Test
    public void testAssignTaskToAUser() throws Exception {
        // get all, check task 3, user 2
        mockMvc.perform(get("/v1/tasks")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[2].user").value(2));

        // set task 3, user 3
        mockMvc.perform(post("/v1/tasks/{taskId}/assign/{userId}", "3", "3"))
                .andExpect(status().isOk());

        // check task 3, user 3
        mockMvc.perform(get("/v1/tasks/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user").value(3));
    }

    @Test
    public void testCompleteTask() throws Exception {
        // check task 1 is not complete
        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.complete").value(false));

        // set task 1 as complete
        mockMvc.perform(post("/v1/tasks/complete/1"))
                .andExpect(status().isOk());

        // test task 1 is complete
        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.complete").value(true))
                .andExpect(jsonPath("$.completed").exists());
    }

    @Test
    public void testGetTaskAssignedToUser() throws Exception {
        mockMvc.perform(get("/v1/tasks/assigned/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].user", hasSize(2)))
                .andExpect(jsonPath("$[0].user").value(1))
                .andExpect(jsonPath("$[1].user").value(1));
    }
}