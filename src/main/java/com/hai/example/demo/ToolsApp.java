package com.hai.example.demo;

import com.hai.example.demo.model.ItemBookmark;
import com.hai.example.demo.model.Util;
import com.hai.example.demo.repository.ItemBookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToolsApp  {
    @Autowired
    static Util util;

    private static final String COMMA_DELIMITER = ",";

    /**
     * Test
     * 1. Write to Redis with normal mode
     * 2. Write to Redis with Webflux
     *
     * @param args
     */
    public static void main(String[] args) {

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/files/itembookmark.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(records.size());
        records.forEach((v) -> {
            System.out.println(v);
            System.out.println("size: " + v.size());
            System.out.println(v.get(0));
            System.out.println(v.get(2).trim());
            System.out.println(v.get(3).trim());
            System.out.println(v.get(4).trim());
            System.out.println(v.get(5).trim());
            System.out.println(v.get(6).trim());
        });


        System.out.println("Finished!");
    }
}

