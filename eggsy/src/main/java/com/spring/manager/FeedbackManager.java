package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.FeedbackDao;
import com.spring.datasource.Feedback;

public class FeedbackManager {
	@JsonIgnore
	private static final Logger logger = Logger
			.getLogger(FeedbackManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	FeedbackDao delegate = new FeedbackDao();
	private ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();

	public ArrayList<Feedback> getAllFeedbacks() {
		try {
			feedbacks = delegate.getAllFeedbacks(conn);
			return feedbacks;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
