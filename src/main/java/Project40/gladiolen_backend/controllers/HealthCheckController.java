package Project40.gladiolen_backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}
