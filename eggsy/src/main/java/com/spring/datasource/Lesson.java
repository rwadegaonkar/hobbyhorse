package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect
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
