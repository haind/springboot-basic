package com.hai.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class JavaType {
    @GetMapping("list-types")
    public void listTypes(){
        List<String> cars = Arrays.asList("car1", "car2", "car3");
//        cars.add("car4"); //Cannot add more element
        System.out.println(cars);

        List<String> stringList = new ArrayList<>();
        stringList.add("A");
        stringList.add("B");
        System.out.println(stringList);

        List<Object> objectList = new ArrayList<>();
        objectList.add("C");
        objectList.add(99);
        objectList.add(new BigDecimal(999999999));
        System.out.println(objectList);
        System.out.println(objectList.iterator());


        String[] strArr = {"A", "B", "C"};
        System.out.print(strArr[1]);

//        ArrayList
    }
}
