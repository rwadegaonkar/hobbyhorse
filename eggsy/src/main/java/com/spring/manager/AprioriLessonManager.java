package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.AprioriLessonDao;
import com.spring.datasource.AprioriLesson;

public class AprioriLessonManager {
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	AprioriLessonDao delegate = new AprioriLessonDao();
	private ArrayList<AprioriLesson> aprioriLessons = new ArrayList<AprioriLesson>();

	public ArrayList<AprioriLesson> getAllAprioriLessons() {
		try {
			aprioriLessons = delegate.getAllAprioriLessons(conn);
			return aprioriLessons;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveAprioriLesson(AprioriLesson aprioriLesson) {
		delegate.saveAprioriLesson(conn, aprioriLesson);
	}

}
