package com.hai.example.demo.repository;

import com.hai.example.demo.model.ItemBookmark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemBookmarkRepository extends CrudRepository<ItemBookmark, String> {

}
