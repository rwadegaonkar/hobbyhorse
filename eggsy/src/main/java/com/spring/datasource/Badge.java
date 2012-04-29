package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class Badge extends CommonBean {
	@JsonProperty
	private int lessonCount;

	public int getLessonCount() {
		return lessonCount;
	}

	public void setLessonCount(int lessonCount) {
		this.lessonCount = lessonCount;
	}
}
