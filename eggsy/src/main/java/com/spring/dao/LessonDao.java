package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.Lesson;
import com.spring.datasource.User;
import com.spring.manager.LessonRowMapper;
import com.spring.util.Query;

public class LessonDao {
	private static LessonRowMapper rowMapper = new LessonRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM lesson WHERE isDeleted=0";
	private static final String INSERT_LESSON = "INSERT INTO USER(name, description, isDeleted, createdBy, lastUpdatedBy, createDate, lastUpdateDate, lessonTypeId) VALUES";
	public Query query = new Query();
	ArrayList<Lesson> lessons = new ArrayList<Lesson>();

	public ArrayList<Lesson> getAllLessons(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			lessons = rowMapper.convertLessonBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lessons;
	}

	public ArrayList<Lesson> saveLesson(Connection conn, Lesson lesson) {
		String insertQuery = INSERT_LESSON + "('" + lesson.getName() + "','"
				+ lesson.getDescription() + "'," + lesson.getIsDeleted() + ",'"
				+ lesson.getCreatedBy() + "','" + lesson.getLastUpdatedBy()
				+ "','" + CURRENT_TIMESTAMP + "','" + CURRENT_TIMESTAMP + "','"
				+ lesson.getLessonTypeId() + "')";
		query.executeUpdate(insertQuery, conn);
		return lessons;
	}
}
