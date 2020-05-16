package com.hai.example.demo;

import com.hai.example.demo.model.Session;

import java.math.BigDecimal;
import java.util.*;


public class JavaTypeApp {
    public static void main(String[] args) {
        listType();
//        mapType();
    }

    public static void mapType() {
        //Map of int,string - dictionary
        System.out.println("//Map of int,string - dictionary");
        Map<Integer, String> map;
        map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");

        System.out.println(map);
        System.out.println(map.get(2));

        //Map of int,Object
        System.out.println("//Map of int,Object");
        Map<Integer, Object> map2 = new HashMap<>();
        map2.put(1, "C");
        map2.put(2, 999);
        map2.put(3, new Session(1, "UUID", "Content"));
        System.out.println(map2);

    }

    //https://stackoverflow.com/questions/2843366/how-to-add-new-elements-to-an-array
    public static void listType() {
        List<String> cars = Arrays.asList("car1", "car2", "car3");
//        cars.add("car4"); //Cannot add more element
        System.out.println(cars);

        //List of string
        System.out.println("//--List of String");
        List<String> stringList = new ArrayList<>();
        stringList.add("A");
        stringList.add("B");
        System.out.println(stringList);

        //List of Objects. This list can add any type in it
        System.out.println("//--List of Objects:");
        List<Object> objectList = new ArrayList<>();
        objectList.add("C");
        objectList.add(99);
        objectList.add(new BigDecimal(999999999));
        System.out.println(objectList);
        System.out.println(objectList.toArray()[1]);

        //Array of string
        System.out.println("//--Array of string");
        String[] strArr = {"A", "B", "C"};
        System.out.println(strArr[1]);

        //Array of Object
        System.out.println("//--Array of Object");
        Object[] objArr = {1, 2, 3, "A", 99.99, true, false};
        System.out.println(objArr[5]);

        //Array of Session object
        System.out.println("//--Array of Session object");
        Session s1 = new Session(1, "UUID", "Content");
        Session s2 = new Session(2, "UUID", "Content");
        Session[] s = {s1,s2};
        System.out.println(s[0]);
        System.out.println(s[1]);

    }
}


