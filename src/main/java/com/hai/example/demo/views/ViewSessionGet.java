package com.hai.example.demo.views;

import java.io.Serializable;
import java.util.Map;

import com.hai.example.demo.model.Session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ViewSessionGet implements Serializable {
    private String status;
    private String message;
    private Iterable sessions;
}