package com.spring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.spring.datasource.FriendsList;
import com.spring.manager.FriendsListRowMapper;
import com.spring.util.Query;

public class FriendsListDao {
	private static FriendsListRowMapper rowMapper = new FriendsListRowMapper();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static Date date = new Date();
	private static final String CURRENT_TIMESTAMP = dateFormat.format(date);
	private static final String SELECT_ALL = "SELECT * FROM friendsList WHERE isDeleted=0";
	public Query query = new Query();
	ArrayList<FriendsList> friendslist = new ArrayList<FriendsList>();

	public ArrayList<FriendsList> getAllFriendsLists(Connection conn) {
		ResultSet rs = query.executeQuery(SELECT_ALL, conn);
		try {
			friendslist = rowMapper.convertFriendsListBean(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friendslist;
	}
}
