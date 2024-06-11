package com.example.fermeri3;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
public class LoginController {

    private static final String THINGSBOARD_BASE_URL = "http://161.53.19.19:45080";
    private static final String THINGSBOARD_LOGIN_URL = THINGSBOARD_BASE_URL + "/api/auth/login";
    private static final String THINGSBOARD_DASHBOARDS_URL = THINGSBOARD_BASE_URL + "/api/tenant/dashboards?pageSize=10&page=0";

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(credentials, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(THINGSBOARD_LOGIN_URL, HttpMethod.POST, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    @GetMapping("/api/proxy/dashboard")
    public ResponseEntity<String> getDashboards(@RequestHeader("Authorization") String authorizationHeader) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-Authorization", authorizationHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(THINGSBOARD_DASHBOARDS_URL, HttpMethod.GET, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
