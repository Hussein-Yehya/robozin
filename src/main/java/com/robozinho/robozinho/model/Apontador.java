package com.robozinho.robozinho.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Apontador implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("results")
	private Result results;

	public Result getResults() {
		return results;
	}

	public void setResults(Result results) {
		this.results = results;
	}
	
	
}
