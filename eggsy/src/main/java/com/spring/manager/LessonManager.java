package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.LessonDao;
import com.spring.datasource.Lesson;

@XmlRootElement(name="lessons")
public class LessonManager {
	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	LessonDao delegate = new LessonDao();	
	
	private ArrayList<Lesson> lessons = new ArrayList<Lesson>();
	
	public ArrayList<Lesson> getLessons() {
		return lessons;
	}	
	
	@XmlElement
	public void setLessons(ArrayList<Lesson> lessonBeanList) {
		this.lessons = lessonBeanList;
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
