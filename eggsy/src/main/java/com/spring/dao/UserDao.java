package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.User;
import com.spring.manager.UserRowMapper;
import com.spring.util.Query;

public class UserDao {
	private static UserRowMapper rowMapper = new UserRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM user WHERE isDeleted=0";
	private static final String SELECT_BY_USERNAME = "SELECT * FROM user WHERE isDeleted=0 and username=";
	private static final String SELECT_BY_ID = "SELECT * FROM user WHERE isDeleted=0 and id=";
	private static final String DELETE_BY_USERNAME = "UPDATE user SET isDeleted=1 where username=";
	private static final String INSERT_USER = "INSERT INTO user(name, description, isDeleted, createdBy, lastUpdatedBy, createDate, lastUpdateDate, username, email, skills, hobbies, location) VALUES";
	public Query query = new Query();
	ArrayList<User> users = new ArrayList<User>();

	public ArrayList<User> getAllUsers(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			users = rowMapper.convertUserBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return users;
	}

	public ArrayList<User> getUserByUsername(Connection conn, String username) {
		ResultSet rs = query.executeQuery(SELECT_BY_USERNAME+"'" + username + "'", conn);
		try {
			users = rowMapper.convertUserBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public ArrayList<User> getUserByUserId(Connection conn, long id) {			
		System.out.println("In DAO class "+id);
		ResultSet rs = query.executeQuery(SELECT_BY_ID+id, conn);
		try {
			users = rowMapper.convertUserBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public void deleteUser(Connection conn, String username) {
		query.executeUpdate(DELETE_BY_USERNAME + "'" + username + "'", conn);
	}

	public ArrayList<User> saveUser(Connection conn, User user) {
		String insertQuery = INSERT_USER + "('" + user.getName() + "','"
				+ user.getDescription() + "'," + user.getIsDeleted() + ",'"
				+ user.getCreatedBy() + "','" + user.getLastUpdatedBy() + "','"
				+ CURRENT_TIMESTAMP + "','" + CURRENT_TIMESTAMP
				+ "','" + user.getUsername() + "','" + user.getEmail() + "','"
				+ user.getSkills() + "','" + user.getHobbies() + "','"
				+ user.getLocation() + "')";
		query.executeUpdate(insertQuery, conn);
		return users;
	}
}
