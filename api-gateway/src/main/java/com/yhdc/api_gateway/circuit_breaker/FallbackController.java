package com.yhdc.api_gateway.circuit_breaker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public ResponseEntity<?> fallback() {
        return new ResponseEntity<>("fallback", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
