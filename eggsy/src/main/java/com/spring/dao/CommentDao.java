package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.Comment;
import com.spring.datasource.CommonBean;
import com.spring.datasource.Lesson;
import com.spring.datasource.Participants;
import com.spring.datasource.User;
import com.spring.manager.CommentRowMapper;
import com.spring.manager.UserRowMapper;
import com.spring.util.Query;

public class CommentDao {
	private static CommentRowMapper rowMapper = new CommentRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM comment WHERE isDeleted=0";
	private static final String INSERT_COMMENT = "INSERT INTO comment(name, description, isDeleted, createdBy, lastUpdatedBy, createDate, lastUpdateDate, userId, lessonId, rating) VALUES";
	private static final String SELECT_TO_CHECK_IF_COMMENTED = "SELECT * FROM comment as c, user as u WHERE c.isDeleted=0 and u.id=c.userId and c.lessonId =";
	private static final String SELECT_LAST_USER_COMMENT = "SELECT * from comment where id=(SELECT max(id) from comment as c, user as u where c.userId=user.id and c.lessonId=";
	public Query query = new Query();
	ArrayList<Comment> comments = new ArrayList<Comment>();

	public ArrayList<Comment> getAllComments(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			comments = rowMapper.convertCommentBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}

	public ArrayList<Comment> saveComment(Connection conn, Comment comment) {
		String insertQuery = INSERT_COMMENT + "('" + comment.getName() + "','"
				+ comment.getDescription() + "'," + comment.getIsDeleted()
				+ ",'" + comment.getCreatedBy() + "','"
				+ comment.getLastUpdatedBy() + "','" + CURRENT_TIMESTAMP
				+ "','" + CURRENT_TIMESTAMP + "','" + comment.getUserId()
				+ "','" + comment.getLessonId() + "','" + comment.getRating()
				+ "')";
		query.executeUpdate(insertQuery, conn);
		return comments;
	}
	
	public ArrayList<Comment> checkCommented(Connection conn,
			String lessonid, String username) {
		String checkQuery = SELECT_TO_CHECK_IF_COMMENTED + lessonid
				+ " and u.username='" + username + "'";
		ResultSet rs = query.executeQuery(checkQuery, conn);
		try {
			comments = rowMapper.convertCommentBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}
	
	public ArrayList<Comment> getLastComment(Connection conn,
			String lessonid, String username) {
		String checkQuery = SELECT_LAST_USER_COMMENT + lessonid
				+ " and u.username='" + username + "')";
		ResultSet rs = query.executeQuery(checkQuery, conn);
		try {
			comments = rowMapper.convertCommentBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}
}
