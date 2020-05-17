package com.hai.example.demo.controllers;

import com.hai.example.demo.model.ItemBookmark;
import com.hai.example.demo.model.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ToolsController {
    private static final String CSV = "src/main/resources/files/itembookmark.csv";

    @Autowired
    Util util;

    private static final String COMMA_DELIMITER = ",";

    @GetMapping("csv")
    public void cvs() {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
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

        parseRedis(records);

        System.out.println("CSV Finished!");
    }

    void parseRedis(List<List<String>> lines) {
        lines.forEach((v) -> {
            System.out.println(v);
            System.out.println("size: " + v.size());

            String sbmItemId = (v.get(0).trim());
            String easyId = (v.get(1).trim());
            ItemBookmark itemBookmark = new ItemBookmark();

            itemBookmark.setId(easyId + "." + sbmItemId);//easy-id + sbmitemid
            itemBookmark.setSbmItemId(Integer.parseInt(sbmItemId));
            itemBookmark.setEasyId(Integer.parseInt(easyId));
            if (!v.get(2).trim().isEmpty()) {
                itemBookmark.setShopId(Integer.parseInt(v.get(2).trim()));
            }
            if (!v.get(3).trim().isEmpty()) {
                itemBookmark.setItemId(Long.parseLong(v.get(3).trim()));
            }
            if (!v.get(4).trim().isEmpty()) {
                itemBookmark.setListId(Integer.parseInt(v.get(4).trim()));
            }
            if (!v.get(5).trim().isEmpty()) {
                itemBookmark.setItemPrice(Integer.parseInt(v.get(5).trim()));
            }
            itemBookmark.setRegTs(v.get(6).trim());
            itemBookmark.setUpdateRegTs((v.get(7).trim()));

            if (!v.get(8).trim().isEmpty()) {
                itemBookmark.setItemType(Integer.parseInt(v.get(8).trim()));
            }
            if (!v.get(9).trim().isEmpty()) {
                itemBookmark.setItemUrl((v.get(9).trim()));
            }
            if (v.size() == 11 && !v.get(10).trim().isEmpty()) {
                itemBookmark.setSbmItemComment((v.get(10).trim()));
            }

            util.writeRedis(itemBookmark);
        });
    }
}
