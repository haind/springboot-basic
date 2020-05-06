package com.hai.example.demo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Session implements Serializable{
	private long id;
	private String uuid;
	private String content;
}