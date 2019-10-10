package com.robozinho.robozinho.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("lat")
	private String lat;
	
	@JsonProperty("lng")
	private String lng;

	public String getLat() {
		return lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
}
