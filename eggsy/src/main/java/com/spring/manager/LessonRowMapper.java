package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.spring.datasource.Lesson;
import com.spring.datasource.User;

public class LessonRowMapper {
	UserManager userManager = new UserManager();

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
			lessonBean.setUserId(rs.getInt("userId"));
			/*ArrayList<User> userList = userManager.getUserByUserId(rs
					.getInt("userId"));
			lessonBean.setUsername(userList.get(0).getName());*/
			lessonBean.setSessionId(rs.getString("sessionId"));
			lessons.add(lessonBean);
		}
		return lessons;
	}
}
