package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class Feedback extends CommonBean {
	@JsonProperty
	private int userId;
	@JsonProperty
	private int rating;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
}
