package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.spring.datasource.Lesson;

public class LessonRowMapper {
	public ArrayList<Lesson> convertLessonBean(ResultSet rs)
			throws SQLException {
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		while (rs.next()) {
			Lesson lessonBean = new Lesson();
			lessonBean.setId(rs.getInt("id"));
			lessonBean.setName(rs.getString("name"));
			lessonBean.setDescription(rs.getString("description"));
			lessonBean.setIsDeleted(rs.getInt("isDeleted"));
			lessonBean.setCreatedBy(rs.getString("createdBy"));
			lessonBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			lessonBean.setCreateDate(rs.getDate("createDate"));
			lessonBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			lessonBean.setLessonTypeId(rs.getInt("lessonTypeId"));
			lessons.add(lessonBean);
		}
		return lessons;
	}
}
