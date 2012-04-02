package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.FriendsListDao;
import com.spring.datasource.FriendsList;

public class FriendsListManager {
	@JsonIgnore
	private static final Logger logger = Logger.getLogger(FriendsListManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	FriendsListDao delegate = new FriendsListDao();
	private ArrayList<FriendsList> friendslists = new ArrayList<FriendsList>();

	public ArrayList<FriendsList> getAllFriendsList() {
		try {
			friendslists = delegate.getAllFriendsLists(conn);
			return friendslists;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
