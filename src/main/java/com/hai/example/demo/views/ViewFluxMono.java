package com.hai.example.demo.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class ViewFluxMono implements Serializable {
    private String status;
    private String message;
    private Object data;
}