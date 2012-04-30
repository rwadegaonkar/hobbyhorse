package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.AprioriLesson;

public class AprioriLessonRowMapper {
	public ArrayList<AprioriLesson> convertAprioriLessonBean(ResultSet rs)
			throws SQLException {
		ArrayList<AprioriLesson> comments = new ArrayList<AprioriLesson>();
		while (rs.next()) {
			AprioriLesson aprioriLessonBean = new AprioriLesson();
			aprioriLessonBean.setId(rs.getInt("id"));
			aprioriLessonBean.setMain(rs.getString("main"));
			aprioriLessonBean.setAssociation(rs.getString("association"));
			aprioriLessonBean.setSupport(rs.getString("support"));
			comments.add(aprioriLessonBean);
		}
		return comments;
	}
}
