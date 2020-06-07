package com.hai.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AopController {
    @GetMapping("/exception")
    public ResponseEntity<String> aopException() {
        throw new IllegalStateException("This is error message");
    }
}
