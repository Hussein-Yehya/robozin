package com.robozinho.robozinho.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("subcategory")
	private SubCategory subCategory;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

}
