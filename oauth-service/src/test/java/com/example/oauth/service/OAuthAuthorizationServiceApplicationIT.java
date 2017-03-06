package com.example.oauth.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebSecurityConfig.class, OAuthAuthorizationServiceApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OAuthAuthorizationServiceApplicationIT {
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@LocalServerPort
	long serverPort;

	@Autowired
	private Filter springSecurityFilterChain;

	@Before
	public void setUp() {

		mockMvc = webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
	}

	@Test
	public void contextLoads() {
	}


	@Test
	public void testGetToken() throws Exception{
		String plainClientCredentials = "saas_app:";
		String base64ClientCredentials = new String(Base64.getEncoder().encodeToString(plainClientCredentials.getBytes()));

		class FormParams {
			String grant_type;
			String username;
			String password;
		}

		FormParams fp = new FormParams();
		fp.grant_type = "password";
		fp.username = "sergei";
		fp.password = "secret";

		mockMvc.perform(post("http://localhost:{serverPort}/oauth/token", serverPort)
						.content("grant_type=password&username=sergei&password=secret")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.header("Authorization", "Basic " +base64ClientCredentials)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("tenantId").isNotEmpty());
	}
}
