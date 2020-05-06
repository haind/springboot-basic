package com.hai.example.demo.views;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class NoContent implements Serializable {
    private String status;
    private String message;
}