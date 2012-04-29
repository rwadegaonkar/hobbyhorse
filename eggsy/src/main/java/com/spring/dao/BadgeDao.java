package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.Badge;
import com.spring.datasource.CommonBean;
import com.spring.manager.BadgeRowMapper;
import com.spring.manager.CommonRowMapper;
import com.spring.util.Query;

public class BadgeDao {
	private static BadgeRowMapper rowMapper = new BadgeRowMapper();
	private static final String SELECT_ALL = "SELECT * FROM badge WHERE isDeleted=0";
	public Query query = new Query();
	ArrayList<Badge> badges = new ArrayList<Badge>();

	public ArrayList<Badge> getAllBadges(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			badges = rowMapper.convertBadgeBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return badges;
	}
}
