package com.hai.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hai.example.demo.model.ItemBookmark;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemBookmarkController {
	@GetMapping("/list-item")
	public List listItem() {
		ItemBookmark obj = new ItemBookmark();
		obj.setItemId(1);
		obj.setItemName("abcd");

		List<Object> list = new ArrayList<Object>();

		list.add(obj);

		ItemBookmark obj2 = new ItemBookmark();
		obj2.setItemId(1);
		obj2.setItemName("abcd");
		list.add(obj2);

		return list;
	}

	@GetMapping("/hashmap")
	public HashMap getHashMap() {

		HashMap<String, String> map = new HashMap<>();
		map.put("key", "value");
		map.put("foo", "bar");
		map.put("aa", "bb");
		return map;
	}

	@GetMapping("/test")
	public void test()
	{
		List<Object> list = new ArrayList<Object>();

		list.add("String abc");
		list.add(Integer.valueOf(1));
		list.add(new Object());

		Object retrievedObject = list.get(2);
		// ItemBookmark itemBookmark = (ItemBookmark)list.get(2); // explicit cast
		System.out.println(list);
		System.out.println(retrievedObject);
	}
}