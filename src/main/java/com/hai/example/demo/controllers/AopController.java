package com.hai.example.demo.controllers;

import com.hai.example.demo.exception.RecordNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AopController {
    @GetMapping("/exception")
    public ResponseEntity<String> aopException() {
        throw new IllegalStateException("This is error message");
    }

    @GetMapping("/math-exception")
    public int clientException(@RequestParam int x) {
        try {
            return 100 / x;
        } catch (Exception e) {
            throw new ArithmeticException("Chia Sai");
        }
    }

    @GetMapping("/notfound-exception")
    public ResponseEntity<String> noFoundException() {
        throw new RecordNotFoundException("NOT FOUND RECORD");
    }
}
