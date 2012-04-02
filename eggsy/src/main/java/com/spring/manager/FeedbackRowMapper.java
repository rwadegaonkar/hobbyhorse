package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.Feedback;

public class FeedbackRowMapper {
	public ArrayList<Feedback> convertFeedbackBean(ResultSet rs)
			throws SQLException {
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		while (rs.next()) {
			Feedback feedbackBean = new Feedback();
			feedbackBean.setId(rs.getInt("id"));
			feedbackBean.setName(rs.getString("name"));
			feedbackBean.setDescription(rs.getString("description"));
			feedbackBean.setIsDeleted(rs.getInt("isDeleted"));
			feedbackBean.setCreatedBy(rs.getString("createdBy"));
			feedbackBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			feedbackBean.setCreateDate(rs.getDate("createDate"));
			feedbackBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			feedbackBean.setUserId(rs.getInt("userId"));
			feedbackBean.setRating(rs.getInt("rating"));
			feedbacks.add(feedbackBean);
		}
		return feedbacks;
	}
}
