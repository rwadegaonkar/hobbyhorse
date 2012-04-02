package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.CommonBean;
import com.spring.datasource.User;

public class CommonRowMapper {
	public ArrayList<CommonBean> convertCommonBean(ResultSet rs)
			throws SQLException {
		ArrayList<CommonBean> commons = new ArrayList<CommonBean>();
		while (rs.next()) {
			CommonBean commonBean = new CommonBean();
			commonBean.setId(rs.getInt("id"));
			commonBean.setName(rs.getString("name"));
			commonBean.setDescription(rs.getString("description"));
			commonBean.setIsDeleted(rs.getInt("isDeleted"));
			commonBean.setCreatedBy(rs.getString("createdBy"));
			commonBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			commonBean.setCreateDate(rs.getDate("createDate"));
			commonBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			commons.add(commonBean);
		}
		return commons;
	}
}
