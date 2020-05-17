package com.hai.example.demo.model;

import com.hai.example.demo.repository.ItemBookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class Util {
    @Autowired
    ItemBookmarkRepository itemBookmarkRepository;

    public void writeRedis(ItemBookmark itemBookmark){
        itemBookmarkRepository.save(itemBookmark);
    }
}
