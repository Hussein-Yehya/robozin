package com.robozinho.robozinho.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("places")
	List<Place> places = new ArrayList<>();

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

}
