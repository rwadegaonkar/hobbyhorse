package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.CommentDao;
import com.spring.datasource.Comment;
import com.spring.datasource.Lesson;
import com.spring.datasource.Participants;

public class CommentManager {
	@JsonIgnore
	private static final Logger logger = Logger.getLogger(CommentManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	CommentDao delegate = new CommentDao();
	private ArrayList<Comment> comments = new ArrayList<Comment>();

	public ArrayList<Comment> getAllComments() {
		try {
			comments = delegate.getAllComments(conn);
			return comments;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public ArrayList<Comment> saveComment(Comment comment) {
		comments = delegate.saveComment(conn, comment);
		return comments;
	}
	
	public ArrayList<Comment> getCommentsByLessonId(String lessonid) {
		comments = delegate.getCommentsByLessonId(conn, lessonid);
		return comments;
	}
	
	public ArrayList<Comment> checkCommented(String lessonid,
			String username) {
		delegate.checkCommented(conn, lessonid, username);
		comments = delegate.getLastComment(conn, lessonid, username);
		return comments;
	}
}
