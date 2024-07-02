package com.surgee.HNG_Task1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hello")
public class AppController {
    private final AppService appService;

    @GetMapping()
    public ResponseEntity<?> getMethodName(@RequestParam String visitor_name, HttpServletRequest request) {
        return appService.getUserDetails(visitor_name, request);
    }
    
}
