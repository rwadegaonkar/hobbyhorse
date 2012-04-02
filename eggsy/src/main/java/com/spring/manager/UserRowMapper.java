package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.CqlRow;

import com.spring.datasource.User;

public class UserRowMapper {
	public ArrayList<User> convertUserBean(ResultSet rs) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		while(rs.next()) {
			User userBean = new User();
			userBean.setId(rs.getInt("id"));
			userBean.setName(rs.getString("name"));
			userBean.setDescription(rs.getString("description"));
			userBean.setIsDeleted(rs.getInt("isDeleted"));
			userBean.setCreatedBy(rs.getString("createdBy"));
			userBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			userBean.setCreateDate(rs.getDate("createDate"));
			userBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			userBean.setUsername(rs.getString("username"));
			userBean.setEmail(rs.getString("email"));
			userBean.setSkills(rs.getString("skills"));
			userBean.setHobbies(rs.getString("hobbies"));
			userBean.setLocation(rs.getString("location"));
			users.add(userBean);
		}
		return users;
	}
}
