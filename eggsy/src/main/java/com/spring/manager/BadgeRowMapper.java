package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.Badge;

public class BadgeRowMapper {
	public ArrayList<Badge> convertBadgeBean(ResultSet rs)
			throws SQLException {
		ArrayList<Badge> comments = new ArrayList<Badge>();
		while (rs.next()) {
			Badge commentBean = new Badge();
			commentBean.setId(rs.getInt("id"));
			commentBean.setName(rs.getString("name"));
			commentBean.setDescription(rs.getString("description"));
			commentBean.setIsDeleted(rs.getInt("isDeleted"));
			commentBean.setCreatedBy(rs.getString("createdBy"));
			commentBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			commentBean.setCreateDate(rs.getDate("createDate"));
			commentBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			commentBean.setLessonCount(rs.getInt("lessonCount"));
			comments.add(commentBean);
		}
		return comments;
	}
}
