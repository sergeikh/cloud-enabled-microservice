package com.example.tasks.client;

import lombok.extern.java.Log;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Log
public class OAuthClient {
    private static String token;
    private static String url = "http://routing-service:5555/api/auth/uaa/oauth/token";
    @Autowired
    RestTemplate restTemplate;

    public String getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic c2Fhc19hcHA6");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("username", "sergei");
        map.add("password", "secret");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK)
            log.warning("OAuth service not accessible");
        else {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = null;
            try {
                root = mapper.readTree(response.getBody());
            } catch (IOException e) {
                log.severe(e.getMessage());
            }
            JsonNode tokenNode = root.path("access_token");

            token = tokenNode.asText();
        }
        return token;
    }

}
