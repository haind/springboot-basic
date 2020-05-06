package com.hai.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ItemBookmark implements Serializable {
    private Integer itemId = 2;
    private String itemName = "Galaxy S20";

    // public List getItemList()
    // {
    // List<Object> list = new ArrayList<Object>();

    // list.add(new ItemBookmark(999, "Samsung S7"));
    // list.add(new ItemBookmark(888, "Samsung S8"));
    // list.add(new ItemBookmark(777, "Samsung S6"));

    // return list;
    // }
}
