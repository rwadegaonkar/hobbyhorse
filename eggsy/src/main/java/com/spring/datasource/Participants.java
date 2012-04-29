package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class Participants extends CommonBean {
	@JsonProperty
	private int userId;
	@JsonProperty
	private int lessonId;
	@JsonProperty
	private int wasAttended;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLessonId() {
		return lessonId;
	}
	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}
	public int getWasAttended() {
		return wasAttended;
	}
	public void setWasAttended(int wasAttended) {
		this.wasAttended = wasAttended;
	}
	
}
