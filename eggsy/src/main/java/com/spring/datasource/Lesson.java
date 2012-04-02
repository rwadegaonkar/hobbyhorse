package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class Lesson extends CommonBean {

	@JsonProperty
	private int lessonTypeId;

	public int getLessonTypeId() {
		return lessonTypeId;
	}

	public void setLessonTypeId(int lessonTypeId) {
		this.lessonTypeId = lessonTypeId;
	}

}
