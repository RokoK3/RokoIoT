package com.example.fermeri3;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@Service
public class ThingsBoardService {

    private static final String THINGSBOARD_BASE_URL = "http://161.53.19.19:45080";
    private static final String THINGSBOARD_DEVICES_URL = THINGSBOARD_BASE_URL + "/api/tenant/devices?pageSize=10&page=0";

    public List<Map<String, Object>> getDevices(String authorizationHeader) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-Authorization", authorizationHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(THINGSBOARD_DEVICES_URL, HttpMethod.GET, entity, (Class<Map<String, Object>>) (Object) Map.class);
        Map<String, Object> responseBody = response.getBody();
        return (List<Map<String, Object>>) responseBody.get("data");
    }

    public ResponseEntity<List<Map<String, Object>>> getCustomerDevices(String authorizationHeader) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-Authorization", authorizationHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map<String, Object>> userResponse = restTemplate.exchange(
                    THINGSBOARD_BASE_URL + "/api/auth/user",
                    HttpMethod.GET,
                    entity,
                    (Class<Map<String, Object>>) (Object) Map.class
            );
            Map<String, Object> userBody = userResponse.getBody();
            String customerId = ((Map<String, String>) userBody.get("customerId")).get("id");

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    THINGSBOARD_BASE_URL + "/api/customer/" + customerId + "/devices?pageSize=10&page=0",
                    HttpMethod.GET,
                    entity,
                    (Class<Map<String, Object>>) (Object) Map.class
            );
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> devices = (List<Map<String, Object>>) responseBody.get("data");
            return ResponseEntity.ok(devices);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    public List<Map<String, Object>> getDeviceAttributes(String authorizationHeader, String deviceId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-Authorization", authorizationHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                THINGSBOARD_BASE_URL + "/api/plugins/telemetry/DEVICE/" + deviceId + "/values/attributes",
                HttpMethod.GET,
                entity,
                (Class<Map<String, Object>>) (Object) List.class
        );
        return (List<Map<String, Object>>) response.getBody();
    }

    public void setDeviceAttributes(String authorizationHeader, String deviceId, Map<String, Object> attributes) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("X-Authorization", authorizationHeader);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(attributes, headers);

        restTemplate.exchange(
                THINGSBOARD_BASE_URL + "/api/plugins/telemetry/DEVICE/" + deviceId + "/attributes/SHARED_SCOPE",
                HttpMethod.POST,
                entity,
                Void.class
        );
    }
}
