package com.example.config.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigurationServiceApplicationTestsIT {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetTasksServiceProperties() throws Exception{
		mockMvc.perform(get("/tasks/prod/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("tasks"))
				.andExpect(jsonPath("$.propertySources[0].source.['spring.datasource.username']").value("user"));
	}
}
