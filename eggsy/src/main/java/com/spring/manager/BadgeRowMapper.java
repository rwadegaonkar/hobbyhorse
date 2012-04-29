package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.Badge;

public class BadgeRowMapper {
	public ArrayList<Badge> convertBadgeBean(ResultSet rs) throws SQLException {
		ArrayList<Badge> comments = new ArrayList<Badge>();
		while (rs.next()) {
			Badge badgeBean = new Badge();
			badgeBean.setId(rs.getInt("id"));
			badgeBean.setName(rs.getString("name"));
			badgeBean.setDescription(rs.getString("description"));
			badgeBean.setIsDeleted(rs.getInt("isDeleted"));
			badgeBean.setCreatedBy(rs.getString("createdBy"));
			badgeBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			badgeBean.setCreateDate(rs.getDate("createDate"));
			badgeBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			badgeBean.setLessonCount(rs.getInt("lessonCount"));
			comments.add(badgeBean);
		}
		return comments;
	}
}
