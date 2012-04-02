package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.ForgotPassword;

public class ForgotPasswordRowMapper {
	public ArrayList<ForgotPassword> convertForgotPasswordBean(ResultSet rs)
			throws SQLException {
		ArrayList<ForgotPassword> forgotpasswords = new ArrayList<ForgotPassword>();
		while (rs.next()) {
			ForgotPassword forgotpasswordBean = new ForgotPassword();
			forgotpasswordBean.setId(rs.getInt("id"));
			forgotpasswordBean.setName(rs.getString("name"));
			forgotpasswordBean.setDescription(rs.getString("description"));
			forgotpasswordBean.setIsDeleted(rs.getInt("isDeleted"));
			forgotpasswordBean.setCreatedBy(rs.getString("createdBy"));
			forgotpasswordBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			forgotpasswordBean.setCreateDate(rs.getDate("createDate"));
			forgotpasswordBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			forgotpasswordBean.setUserId(rs.getInt("userId"));
			forgotpasswordBean.setQuestion(rs.getString("question"));
			forgotpasswordBean.setAnswer(rs.getString("answer"));
			forgotpasswords.add(forgotpasswordBean);
		}
		return forgotpasswords;
	}
}
