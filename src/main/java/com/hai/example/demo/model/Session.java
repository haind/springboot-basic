package com.hai.example.demo.model;

import java.io.Serializable;

public class Session implements Serializable{
	private long id;
	private String content;

	public Session(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}