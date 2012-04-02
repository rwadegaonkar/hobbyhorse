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
}
