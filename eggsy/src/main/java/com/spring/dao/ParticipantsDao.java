package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.Lesson;
import com.spring.datasource.Participants;
import com.spring.datasource.User;
import com.spring.manager.ParticipantsRowMapper;
import com.spring.util.Query;

public class ParticipantsDao {
	private static ParticipantsRowMapper rowMapper = new ParticipantsRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM participants WHERE isDeleted=0";
	private static final String SELECT_TO_CHECK_IF_JOINED = "SELECT * FROM participants as p, user as u WHERE p.isDeleted=0 and u.id=p.userId and p.lessonId =";
	private static final String UPDATE_PARTICIPANT_ATTENDED = "UPDATE participants as p, user as u SET p.wasAttended=1 where u.id=p.userId and p.lessonId=";
	private static final String INSERT_PARTICIPANT = "INSERT INTO participants(name, description, isDeleted, createdBy, lastUpdatedBy, createDate, lastUpdateDate, userId, lessonId, wasAttended) VALUES";
	public Query query = new Query();
	ArrayList<Participants> participants = new ArrayList<Participants>();

	public ArrayList<Participants> getAllParticipants(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			participants = rowMapper.convertParticipantsBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return participants;
	}

	// Method for adding participants when they join a lesson.
	public ArrayList<Participants> saveParticipant(Connection conn,
			Participants participant) {
		String insertQuery = INSERT_PARTICIPANT + "('" + participant.getName()
				+ "','" + participant.getDescription() + "',0,'"
				+ participant.getCreatedBy() + "','"
				+ participant.getLastUpdatedBy() + "','" + CURRENT_TIMESTAMP
				+ "','" + CURRENT_TIMESTAMP + "','" + participant.getUserId()
				+ "','" + participant.getLessonId() + "',0)";
		query.executeUpdate(insertQuery, conn);
		return participants;

	}

	public ArrayList<Participants> checkIfLessonJoined(Connection conn,
			String lessonid, String username) {
		String checkQuery = SELECT_TO_CHECK_IF_JOINED + lessonid
				+ " and u.username='" + username + "'";
		ResultSet rs = query.executeQuery(checkQuery, conn);
		try {
			participants = rowMapper.convertParticipantsBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return participants;
	}

	public ArrayList<Participants> updateParticipantAttendance(Connection conn,
			Participants participant) {
		query.executeUpdate(
				UPDATE_PARTICIPANT_ATTENDED + participant.getLessonId()
						+ " AND u.username='" + participant.getCreatedBy()
						+ "'", conn);
		return participants;
	}

}
