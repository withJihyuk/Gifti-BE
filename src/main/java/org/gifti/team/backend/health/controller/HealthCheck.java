package org.gifti.team.backend.health.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheck {
    @GetMapping("/health")
    public Map<String, Boolean> checkingHealth() {
        return Map.of("isOnline",true);
    }
}
