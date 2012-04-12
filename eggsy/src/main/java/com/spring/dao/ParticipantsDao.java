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
	//Query to add participants in the participant table
	private static final String INSERT_PARTICIPANT = "INSERT INTO participants(name, description, isDeleted, createdBy, lastUpdatedBy, createDate, lastUpdateDate, userId, lessonId) VALUES";
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
	
//Method for adding participants when they join a lesson.	
	public ArrayList<Participants> saveParticipant(Connection conn, Participants participant) {
		User user = new User();
		user.setId(participant.getUserId());
		String insertQuery = INSERT_PARTICIPANT + "('" + user.getName() + "','"
				+ user.getDescription() + "'," + user.getIsDeleted() + ",'"
				+ user.getCreatedBy() + "','" + user.getLastUpdatedBy()
				+ "','" + CURRENT_TIMESTAMP + "','" + CURRENT_TIMESTAMP + "','"
				+ participant.getUserId() + "','"
				+ participant.getLessonId() + "')";
		query.executeUpdate(insertQuery, conn);
		return participants;	
	
	}
		
}
