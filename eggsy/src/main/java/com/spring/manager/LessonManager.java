package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.spring.conf.ConnMysql;
import com.spring.dao.LessonDao;
import com.spring.datasource.Lesson;

@XmlRootElement(name = "lessons")
public class LessonManager {
	@JsonIgnore
	private static final Logger logger = Logger.getLogger(LessonManager.class);
	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	LessonDao delegate = new LessonDao();	
	
	private ArrayList<Lesson> lessons = new ArrayList<Lesson>();
	public ArrayList<Lesson> getLessons() {
		return lessons;
	}
	public void setLessons(ArrayList<Lesson> lessons) {
		this.lessons = lessons;
	}
	
	public ArrayList<Lesson> getAllLessons() {
		try {
			lessons = delegate.getAllLessons(conn);
			return lessons;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveLesson(Lesson lesson) {
		lessons = delegate.saveLesson(conn, lesson);
	}
	
	public ArrayList<Lesson> getLessonsByLessonTypeId(int lessonId) {
		try {
			lessons = delegate.getLessonsByLessonTypeId(conn, lessonId);
			return lessons;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
