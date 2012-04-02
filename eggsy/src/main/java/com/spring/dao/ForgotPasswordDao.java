package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.ForgotPassword;
import com.spring.datasource.Lesson;
import com.spring.manager.ForgotPasswordRowMapper;
import com.spring.util.Query;

public class ForgotPasswordDao {
	private static ForgotPasswordRowMapper rowMapper = new ForgotPasswordRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM forgotPassword WHERE isDeleted=0";
	public Query query = new Query();
	ArrayList<ForgotPassword> forgotpasswords = new ArrayList<ForgotPassword>();

	public ArrayList<ForgotPassword> getAllForgotPasswords(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			forgotpasswords = rowMapper.convertForgotPasswordBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return forgotpasswords;
	}
}
