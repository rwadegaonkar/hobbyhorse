package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.Login;

public class LoginRowMapper {
	public ArrayList<Login> convertLoginBean(ResultSet rs) throws SQLException {
		ArrayList<Login> logins = new ArrayList<Login>();
		while (rs.next()) {
			Login loginBean = new Login();
			loginBean.setId(rs.getInt("id"));
			loginBean.setName(rs.getString("name"));
			loginBean.setDescription(rs.getString("description"));
			loginBean.setIsDeleted(rs.getInt("isDeleted"));
			loginBean.setCreatedBy(rs.getString("createdBy"));
			loginBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			loginBean.setCreateDate(rs.getDate("createDate"));
			loginBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			loginBean.setLoginTypeId(rs.getInt("loginTypeId"));
			logins.add(loginBean);
		}
		return logins;
	}
}
