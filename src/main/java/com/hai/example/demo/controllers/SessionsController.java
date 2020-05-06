package com.hai.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.hai.example.demo.model.Session;
import com.hai.example.demo.views.NoContent;
import com.hai.example.demo.views.ViewJsonResponse;
import com.hai.example.demo.views.ViewSessionGet;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SessionsController {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	Map<String, Session> sessionMap;

	@GetMapping("/greeting")
	public Session greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Session(counter.incrementAndGet(), UUID.randomUUID().toString(), String.format(template, name));
	}

	@GetMapping("/iterable")
	public @ResponseBody Iterable<Map> iterable() {
		Session s1 = new Session(1, "uuid", "content");
		Session s2 = new Session(2, "uuid", "content");

		sessionMap = new HashMap<>();
		sessionMap.put("1", s1);
		sessionMap.put("2", s2);

		List<Object> list = new ArrayList<Object>();
		list.add(s1);
		list.add(s2);

		return (Iterable) list;
	}

	@GetMapping("/iterable-view")
	public ResponseEntity<ViewSessionGet> iterableView() {
		Session s1 = new Session(1, "uuid", "content");
		Session s2 = new Session(2, "uuid", "content");

		sessionMap = new HashMap<>();
		sessionMap.put("1", s1);
		sessionMap.put("2", s2);

		List<Object> list = new ArrayList<Object>();
		list.add(s1);
		list.add(s2);

		return ResponseEntity.status(HttpStatus.OK).body(new ViewSessionGet("Ok", "List sessions", (Iterable) list));
	}

	// @GetMapping("/sessions")
	// public ResponseEntity<ViewSessionGet> getAllSessions() {
	// 	// return ResponseEntity.status(HttpStatus.OK).body(new ViewSessionGet("Ok", "List sessions", sessionMap));
	// }

	@GetMapping("/session/{id}")
	public ResponseEntity<Session> getSession(@PathVariable String id) {
		if (sessionMap != null && sessionMap.containsKey(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(sessionMap.get(id));
		}
		return new ResponseEntity<Session>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(path = "/create/{key}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Session create(@PathVariable String key, @RequestBody Session session) {
		return new Session(session.getId(), UUID.randomUUID().toString(),
				String.format(template, session.getContent() + " key:" + key));
	}

	@PostMapping("/session-create/{key}")
	public ViewJsonResponse createMap(@PathVariable String key, @RequestBody Session session) {
		String uuid = UUID.randomUUID().toString();

		session = new Session(session.getId(), uuid, String.format(template, session.getContent() + " key:" + key));

		if (sessionMap == null) {
			sessionMap = new HashMap<>();
		}
		sessionMap.put(key, session);

		return new ViewJsonResponse("Ok", "created", session);
	}

	@PutMapping(path = "/session-update/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Session update(@PathVariable String id, @RequestBody Session session) {
		session = new Session(session.getId(), UUID.randomUUID().toString(),
				String.format(template, session.getContent() + " Updated!"));
		sessionMap.put(id, session);

		return session;
	}
}