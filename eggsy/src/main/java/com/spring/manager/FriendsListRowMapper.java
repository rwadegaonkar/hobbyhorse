package com.spring.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.spring.datasource.FriendsList;

public class FriendsListRowMapper {
	public ArrayList<FriendsList> convertFriendsListBean(ResultSet rs)
			throws SQLException {
		ArrayList<FriendsList> friendslists = new ArrayList<FriendsList>();
		while (rs.next()) {
			FriendsList friendsListBean = new FriendsList();
			friendsListBean.setId(rs.getInt("id"));
			friendsListBean.setName(rs.getString("name"));
			friendsListBean.setDescription(rs.getString("description"));
			friendsListBean.setIsDeleted(rs.getInt("isDeleted"));
			friendsListBean.setCreatedBy(rs.getString("createdBy"));
			friendsListBean.setLastUpdatedBy(rs.getString("lastUpdatedBy"));
			friendsListBean.setCreateDate(rs.getDate("createDate"));
			friendsListBean.setLastUpdateDate(rs.getDate("lastUpdateDate"));
			friendsListBean.setMainUserId(rs.getInt("mainUserId"));
			friendsListBean.setFriendUserId(rs.getInt("friendUserId"));
			friendslists.add(friendsListBean);
		}
		return friendslists;
	}
}
