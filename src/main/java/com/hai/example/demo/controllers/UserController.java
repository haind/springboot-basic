package com.hai.example.demo.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import com.hai.example.demo.model.User;
import com.hai.example.demo.repository.UserRepository;
import com.hai.example.demo.views.ViewSessionGet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// @Controller // This means that this class is a Controller
@RestController
@RequestMapping("/user") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @PostMapping("/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser(@Valid @RequestBody User u) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        for (int i = 0; i < 10; i++) {
            User n = new User();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            n.setName(u.getName() + " - " + now);
            n.setEmail(u.getEmail());
            userRepository.save(n);
        }

        return "Saved";
    }

    @GetMapping("/all")
    public ResponseEntity<ViewSessionGet> getAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "email") String sort) {
        Pageable paging = PageRequest.of(page, 2, Sort.by(sort));
        Iterable result = userRepository.findAll(paging);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ViewSessionGet("Ok", "List sessions", result));
    }

    @GetMapping("/{id}")
    public @ResponseBody String getUserID(@PathVariable int id) {
        // This returns a JSON or XML with the users
        return userRepository.findById(id).toString();
    }

    @GetMapping("/user-name/{userName}")
    public @ResponseBody Iterable<User> getUserName(@PathVariable String userName) {
        // This returns a JSON or XML with the users
        return userRepository.findByName(userName);
    }

    @DeleteMapping(path = "/delete/{id}")
    public Boolean deleteByIdm(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch(Exception exception) {
            return false;
        }
    }
}