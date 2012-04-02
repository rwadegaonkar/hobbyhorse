package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.LessonDao;
import com.spring.datasource.Lesson;

public class LessonManager {
	@JsonIgnore
	private static final Logger logger = Logger.getLogger(LessonManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	LessonDao delegate = new LessonDao();
	private ArrayList<Lesson> lessons = new ArrayList<Lesson>();

	public ArrayList<Lesson> getAllLessons() {
		try {
			lessons = delegate.getAllLessons(conn);
			return lessons;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
