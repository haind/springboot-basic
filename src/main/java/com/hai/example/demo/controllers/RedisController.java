package com.hai.example.demo.controllers;

import com.hai.example.demo.model.ItemBookmark;
import com.hai.example.demo.model.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RedisController {
    @Autowired
    Util util;

    @GetMapping("redis")
    void redis(@RequestParam String name){
        ItemBookmark itemBookmark = new ItemBookmark();

        util.writeRedis(itemBookmark);
    }
}
