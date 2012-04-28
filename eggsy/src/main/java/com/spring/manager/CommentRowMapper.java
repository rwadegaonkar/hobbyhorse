package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.Comment;

public class CommentRowMapper {
	public ArrayList<Comment> convertCommentBean(ResultSet rs)
			throws SQLException {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		while (rs.next()) {
			Comment commentBean = new Comment();
			commentBean.setId(rs.getInt("id"));
			commentBean.setName(rs.getString("name"));
			commentBean.setDescription(rs.getString("description"));
			commentBean.setIsDeleted(rs.getInt("isDeleted"));
			commentBean.setCreatedBy(rs.getString("createdBy"));
			commentBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			commentBean.setCreateDate(rs.getDate("createDate"));
			commentBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			commentBean.setUserId(rs.getInt("userId"));
			commentBean.setLessonId(rs.getInt("lessonId"));
			commentBean.setRating(rs.getInt("rating"));
			comments.add(commentBean);
		}
		return comments;
	}
}
