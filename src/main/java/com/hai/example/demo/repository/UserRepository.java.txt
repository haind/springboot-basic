package com.hai.example.demo.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

import com.hai.example.demo.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    
    List<User> findByName(String name);

    User findById(int id);
    
}