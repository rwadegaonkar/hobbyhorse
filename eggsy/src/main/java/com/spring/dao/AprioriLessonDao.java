package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.AprioriLesson;
import com.spring.datasource.User;
import com.spring.manager.AprioriLessonRowMapper;
import com.spring.util.Query;

public class AprioriLessonDao {
	private static AprioriLessonRowMapper rowMapper = new AprioriLessonRowMapper();
	private static final String SELECT_ALL = "SELECT * FROM apriori";
	private static final String INSERT_APRIORI = "INSERT INTO apriori(main, association, support) VALUES";
	public Query query = new Query();
	ArrayList<AprioriLesson> aprioriLessons = new ArrayList<AprioriLesson>();

	public ArrayList<AprioriLesson> getAllAprioriLessons(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			aprioriLessons = rowMapper.convertAprioriLessonBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aprioriLessons;
	}

	public void saveAprioriLesson(Connection conn, AprioriLesson aprioriLesson) {
		String insertQuery = INSERT_APRIORI + "('" + aprioriLesson.getMain()
				+ "','" + aprioriLesson.getAssociation() + "','"
				+ aprioriLesson.getSupport() + "')";
		query.executeUpdate(insertQuery, conn);
	}
}
