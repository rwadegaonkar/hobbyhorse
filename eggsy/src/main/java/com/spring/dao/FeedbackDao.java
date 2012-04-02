package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.Feedback;
import com.spring.manager.FeedbackRowMapper;
import com.spring.util.Query;

public class FeedbackDao {
	private static FeedbackRowMapper rowMapper = new FeedbackRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM feedback WHERE isDeleted=0";
	public Query query = new Query();
	ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();

	public ArrayList<Feedback> getAllFeedbacks(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			feedbacks = rowMapper.convertFeedbackBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feedbacks;
	}
}
