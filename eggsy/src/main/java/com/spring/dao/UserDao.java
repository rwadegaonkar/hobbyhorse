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
	private static final String SELECT_BY_USERNAME_AND_PASSWORD = "SELECT * FROM user WHERE isDeleted=0 and loginTypeId=1 and username=";
	private static final String SELECT_BY_ID = "SELECT * FROM user WHERE isDeleted=0 and id=";
	private static final String DELETE_BY_USERNAME = "UPDATE user SET isDeleted=1 where username=";
	private static final String AVERAGE_RATING_FOR_USERID = "SELECT AVG( c.rating ) as rating FROM comment as c,user as u WHERE userId=u.id and u.id =";
	private static final String INSERT_USER = "INSERT INTO user(name, description, isDeleted, createdBy, lastUpdatedBy, createDate, lastUpdateDate, username, password, email, skills, hobbies, location, loginTypeId) VALUES";
	private static final String SELECT_BY_USERS_BY_LESSONID = "SELECT u.* from user as u, participants as p WHERE p.userId=u.id and p.lessonId=";

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
		ResultSet rs = query.executeQuery(SELECT_BY_USERNAME + "'" + username
				+ "'", conn);
		try {
			users = rowMapper.convertUserBean(rs);
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<User> getUserByUserId(Connection conn, long id)
			throws SQLException {
		ResultSet rs = query.executeQuery(SELECT_BY_ID + id, conn);
		users = rowMapper.convertUserBean(rs);
		return users;
	}

	public ArrayList<User> getUserByUsernameAndPassword(Connection conn,
			String username, String password) {
		ResultSet rs = query.executeQuery(SELECT_BY_USERNAME_AND_PASSWORD + "'"
				+ username + "'" + " and password=" + "'" + password + "'",
				conn);
		try {
			users = rowMapper.convertUserBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	public ArrayList<User> getTotalUsersParticipatedByLessonId(Connection conn,
			int lessonid) {
		ResultSet rs = query.executeQuery(SELECT_BY_USERS_BY_LESSONID
				+ lessonid, conn);
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
		ArrayList<User> checkUser = getUserByUsername(conn, user.getUsername());
		if (checkUser.size() == 0) {
			String insertQuery = INSERT_USER + "('" + user.getName() + "','"
					+ user.getDescription() + "'," + user.getIsDeleted() + ",'"
					+ user.getCreatedBy() + "','" + user.getLastUpdatedBy()
					+ "','" + CURRENT_TIMESTAMP + "','" + CURRENT_TIMESTAMP
					+ "','" + user.getUsername() + "','" + user.getPassword()
					+ "','" + user.getEmail() + "','" + user.getSkills()
					+ "','" + user.getHobbies() + "','" + user.getLocation()
					+ "'," + user.getLoginTypeId() + ")";

			query.executeUpdate(insertQuery, conn);
			ResultSet rs = query.executeQuery(
					SELECT_BY_USERNAME + "'" + user.getUsername() + "'", conn);
			try {
				users = rowMapper.convertUserBean(rs);
				return users;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public int getAverageRating(Connection conn, int userid) {
		String checkQuery = AVERAGE_RATING_FOR_USERID + userid;
		ResultSet rs = query.executeQuery(checkQuery, conn);
		long rating = 0L;
		try {
			while (rs.next()) {
				rating = rs.getLong("rating");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(rating + "");
	}

}
