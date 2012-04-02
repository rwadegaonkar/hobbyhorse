package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.Login;
import com.spring.manager.LoginRowMapper;
import com.spring.util.Query;

public class LoginDao {
	private static LoginRowMapper rowMapper = new LoginRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM loginType WHERE isDeleted=0";
	public Query query = new Query();
	ArrayList<Login> logins = new ArrayList<Login>();

	public ArrayList<Login> getAllLogins(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			logins = rowMapper.convertLoginBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logins;
	}
}
