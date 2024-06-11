package com.example.fermeri3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
public class DeviceController {

    @Autowired
    private ThingsBoardService thingsBoardService;

    @GetMapping("/api/devices")
    public ResponseEntity<List<Map<String, Object>>> getDevices(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            List<Map<String, Object>> devices = thingsBoardService.getDevices(authorizationHeader);
            return ResponseEntity.ok(devices);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 403) {
                return thingsBoardService.getCustomerDevices(authorizationHeader);
            }
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    @GetMapping("/api/devices/{deviceId}/attributes")
    public ResponseEntity<List<Map<String, Object>>> getDeviceAttributes(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String deviceId) {
        try {
            List<Map<String, Object>> attributes = thingsBoardService.getDeviceAttributes(authorizationHeader, deviceId);
            return ResponseEntity.ok(attributes);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    @PostMapping("/api/devices/{deviceId}/attributes")
    public ResponseEntity<Void> setDeviceAttributes(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String deviceId, @RequestBody Map<String, Object> attributes) {
        try {
            thingsBoardService.setDeviceAttributes(authorizationHeader, deviceId, attributes);
            return ResponseEntity.ok().build();
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
}
