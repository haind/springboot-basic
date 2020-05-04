package com.hai.example.demo.controllers;

import java.util.concurrent.atomic.AtomicLong;

import com.hai.example.demo.model.Session;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionsController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Session greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Session(counter.incrementAndGet(), String.format(template, name));
	}

	@PostMapping("/create/{key}")
	public Session create(@PathVariable String key, @RequestBody Session session) {
		return new Session(session.getId(), String.format(template, session.getContent() + " key:" + key));
	}
}