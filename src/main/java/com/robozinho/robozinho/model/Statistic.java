package com.robozinho.robozinho.model;

import java.io.Serializable;

public class Statistic implements Serializable {

	private static final long serialVersionUID = 1L;
	private String rating;
	private String reviews;
	private String thumbsUp;
	private String thumbsDown;

	public String getRating() {
		return rating;
	}

	public String getReviews() {
		return reviews;
	}

	public String getThumbsUp() {
		return thumbsUp;
	}

	public String getThumbsDown() {
		return thumbsDown;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public void setReviews(String reviews) {
		this.reviews = reviews;
	}

	public void setThumbsUp(String thumbsUp) {
		this.thumbsUp = thumbsUp;
	}

	public void setThumbsDown(String thumbsDown) {
		this.thumbsDown = thumbsDown;
	}

}
