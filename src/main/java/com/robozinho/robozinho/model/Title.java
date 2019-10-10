package com.robozinho.robozinho.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Title {
	
	@JsonProperty("title")
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	
}
