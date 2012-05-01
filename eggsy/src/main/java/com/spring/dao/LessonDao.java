package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.Lesson;
import com.spring.manager.LessonRowMapper;
import com.spring.util.Query;

public class LessonDao {
	private static LessonRowMapper rowMapper = new LessonRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM lesson WHERE isDeleted=0 and CONCAT_WS(' ', eventDate, eventTime ) > CURRENT_TIMESTAMP - INTERVAL 2 HOUR";
	private static final String SELECT_BY_LESSONTYPE_ID = "SELECT * FROM lesson WHERE isDeleted=0 and CONCAT_WS(' ', eventDate, eventTime ) > CURRENT_TIMESTAMP - INTERVAL 2 HOUR and lessonTypeId=";
	private static final String INSERT_LESSON = "INSERT INTO lesson(name, description, isDeleted, createdBy, lastUpdatedBy, createDate, lastUpdateDate, eventDate, eventTime, lessonTypeId, userId, sessionId, isLive) VALUES";
	private static final String SELECT_BY_USERNAME = "SELECT l.* FROM lesson as l, user as u, participants as p WHERE l.isDeleted=0 and p.userId=u.id and p.lessonId=l.id and p.isDeleted=0 and CONCAT_WS(' ', l.eventDate, l.eventTime ) > CURRENT_TIMESTAMP - INTERVAL 2 HOUR and u.username=";
	private static final String SELECT_LAST_LESSON = "SELECT * FROM lesson WHERE id = (SELECT MAX( id ) FROM lesson ) and userId=";
	private static final String SELECT_BY_LESSONID = "SELECT * FROM lesson WHERE isDeleted=0 and CONCAT_WS(' ', eventDate, eventTime ) > CURRENT_TIMESTAMP - INTERVAL 2 HOUR and id=";
	private static final String UPDATE_LESSON_ISLIVE = "UPDATE lesson SET isLive=";
	private static final String LESSONS_ATTENDED_BY_USER = "Select l.* from participants as p, lesson as l, user as u where p.lessonId=l.id and p.userId=u.id and p.wasAttended=1 and u.username=";
	private static final String SELECT_SUGGESTED_LESSONS = "Select l.* from lesson as l, lessonType as lt where l.lessonTypeId=lt.id and CONCAT_WS(' ', l.eventDate, l.eventTime ) > CURRENT_TIMESTAMP - INTERVAL 2 HOUR and (LOWER(l.name) like";
	private static final String SELECT_SUGGESTED_LESSONS_APRIORI = "SELECT l.*,a.support from lesson as l, apriori as a WHERE CONCAT_WS(' ', l.eventDate, l.eventTime ) > CURRENT_TIMESTAMP - INTERVAL 2 HOUR and l.id=a.association and a.main=";
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

	public ArrayList<Lesson> getLessonsByLessonTypeId(Connection conn,
			int lessonId) {
		ResultSet rs = query.executeQuery(SELECT_BY_LESSONTYPE_ID + lessonId,
				conn);
		try {
			lessons = rowMapper.convertLessonBean(rs);
			System.out.println(lessons.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lessons;
	}

	public ArrayList<Lesson> getLessonsByUsername(Connection conn,
			String username) {
		ResultSet rs = query.executeQuery(SELECT_BY_USERNAME + "'" + username
				+ "'", conn);
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
				+ lesson.getEventDate() + "','" + lesson.getEventTime() + "','"
				+ lesson.getLessonTypeId() + "','" + lesson.getUserId() + "','"
				+ lesson.getSessionId() + "'," + lesson.getIsLive() + ")";
		query.executeUpdate(insertQuery, conn);
		return lessons;
	}

	public ArrayList<Lesson> updateLessonIsLive(Connection conn, Lesson lesson) {
		query.executeUpdate(UPDATE_LESSON_ISLIVE + lesson.getIsLive()
				+ " WHERE id=" + lesson.getId(), conn);
		return lessons;
	}

	public ArrayList<Lesson> getLastLessonByUser(Connection conn, int userId) {
		ResultSet rs = query.executeQuery(SELECT_LAST_LESSON + userId, conn);
		try {
			lessons = rowMapper.convertLessonBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lessons;
	}

	public ArrayList<Lesson> getLessonByLessonId(Connection conn, int id) {
		ResultSet rs = query.executeQuery(SELECT_BY_LESSONID + id, conn);
		try {
			lessons = rowMapper.convertLessonBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lessons;
	}

	public ArrayList<Lesson> getLessonsAttendedByUser(Connection conn,
			String username) {
		ResultSet rs = query.executeQuery(LESSONS_ATTENDED_BY_USER + "'"
				+ username + "'", conn);
		try {
			lessons = rowMapper.convertLessonBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lessons;
	}

	public ArrayList<Lesson> getSuggestedLesson(Connection conn, String name,
			String category, String username) {
		ResultSet rs = query
				.executeQuery(
						SELECT_SUGGESTED_LESSONS
								+ "'%"
								+ name
								+ "%' or LOWER(l.name) like '%"
								+ category
								+ "%' or LOWER(lt.name) like '%"
								+ name
								+ "%' or LOWER(lt.name) like '%"
								+ category
								+ "%') AND l.id NOT IN ( SELECT p.lessonId FROM participants AS p, user as u WHERE p.userId = u.id and u.username= '"
								+ username + "')  group by l.id", conn);
		try {
			lessons = rowMapper.convertLessonBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lessons;
	}
	

	public ArrayList<Lesson> getSuggestedLessonsApriori(Connection conn,
			String lessonid) {
		ResultSet rs = query.executeQuery(SELECT_SUGGESTED_LESSONS_APRIORI + "'"
				+ lessonid + "'", conn);
		try {
			lessons = rowMapper.convertLessonBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lessons;
	}
}
