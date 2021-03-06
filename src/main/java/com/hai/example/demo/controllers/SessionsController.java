package com.hai.example.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hai.example.demo.dto.SessionDTO;
import com.hai.example.demo.mapper.SessionMapper;
import com.hai.example.demo.model.Session;
import com.hai.example.demo.views.ViewJsonResponse;
import com.hai.example.demo.views.ViewSessionGet;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SessionsController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    Map<String, Session> sessionMap;
    Map<String, SessionDTO> newSessionMap;

    @GetMapping("/greeting")
    public Session greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Session(counter.incrementAndGet(), UUID.randomUUID().toString(), String.format(template, name));
    }

    @GetMapping("/mapping")
    public void mapping() {
        Session session = new Session(123, "UUID-1234", "Content");

        SessionMapper mapper = Mappers.getMapper(SessionMapper.class);
        SessionDTO dto = mapper.sessionToSessionDTO(session);

        //Input session => Output session DTO View
        System.out.println(session);
        System.out.println(dto);
    }

    @GetMapping("/iterable")
    public @ResponseBody
    Iterable<Map> iterable() {
        Session s1 = new Session(1, "uuid", "content");
        Session s2 = new Session(2, "uuid", "content");

        sessionMap = new HashMap<>();
        sessionMap.put("1", s1);
        sessionMap.put("2", s2);

        List<Object> list = new ArrayList<Object>();
        list.add(s1);
        list.add(s2);

        List<String> cars = Arrays.asList("car1", "car2");
        System.out.println(cars);


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

    @GetMapping("/sessions")
    public ResponseEntity<Map> getAllSessions() {
        newSessionMap = new HashMap<>();
        SessionMapper mapper = Mappers.getMapper(SessionMapper.class);

        //Input: a Map of session entries
        //Output: a Map of DTO session entries
        newSessionMap = mapper.sessionToSessionDTO(sessionMap);
        return ResponseEntity.status(HttpStatus.OK).body(newSessionMap);
    }

    @GetMapping("/session/{id}")
    public ResponseEntity<SessionDTO> getSession(@PathVariable String id) {
        if (sessionMap != null && sessionMap.containsKey(id)) {
            Session session = sessionMap.get(id);

            SessionMapper mapper = Mappers.getMapper(SessionMapper.class);
            SessionDTO sessionView = mapper.sessionToSessionDTO(session);
            return ResponseEntity.status(HttpStatus.OK).body(sessionView);
        }

        return new ResponseEntity<SessionDTO>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/create/{key}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public SessionDTO create(@PathVariable String key, @RequestBody Session session) {
        //Create
        Session newSession = new Session(session.getId(), UUID.randomUUID().toString(),
                String.format(template, session.getContent() + " key:" + key));
        //Map to view
        SessionMapper mapper = Mappers.getMapper(SessionMapper.class);

        SessionDTO sessionDTO = mapper.sessionToSessionDTO(newSession);

        return sessionDTO;
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

    @PutMapping(path = "/session-update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Session update(@PathVariable String id, @RequestBody Session session) {
        session = new Session(session.getId(), UUID.randomUUID().toString(),
                String.format(template, session.getContent() + " Updated!"));
        sessionMap.put(id, session);

        return session;
    }

    @GetMapping("/session/json")
    public void json() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        //Create string of JSON array
        String strJson = "[{\"id\":0,\"uuid\":\"fd03c8b9-ce78-4a96-8fb8-2e77da63bb82\",\"content\":\"Hello, abc key:5!\"},{\"id\":0,\"uuid\":\"d133058f-8985-4032-becc-98efd1ac3900\",\"content\":\"Hello, abc key:6!\"}]";
        //Read JSON and convert into Session Array
        List<Session> sessions = Arrays.asList(mapper.readValue(strJson, Session[].class));
        System.out.println(sessions);
        System.out.println(Arrays.asList(sessions));

        //Convert DTO from Session Array
        SessionMapper mapper1 = Mappers.getMapper(SessionMapper.class);
        List<SessionDTO> sessionDTOS = mapper1.sessionToSessionDTO(sessions);
        System.out.println(sessionDTOS);

    }
}