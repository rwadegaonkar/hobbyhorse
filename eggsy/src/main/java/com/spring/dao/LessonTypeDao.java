package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.CommonBean;
import com.spring.manager.CommonRowMapper;
import com.spring.util.Query;

public class LessonTypeDao
{
	private static CommonRowMapper rowMapper = new CommonRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM lessonType WHERE isDeleted=0";
	public Query query = new Query();
	ArrayList<CommonBean> lessonTypes = new ArrayList<CommonBean>();

	public ArrayList<CommonBean> getAllLessonTypes(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			lessonTypes = rowMapper.convertCommonBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lessonTypes;
	}
}
