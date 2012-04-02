package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class FriendsList extends CommonBean {
	@JsonProperty
	private int mainUserId;	
	@JsonProperty
	private int friendUserId;
	
	public int getMainUserId() {
		return mainUserId;
	}
	public void setMainUserId(int mainUserId) {
		this.mainUserId = mainUserId;
	}
	public int getFriendUserId() {
		return friendUserId;
	}
	public void setFriendUserId(int friendUserId) {
		this.friendUserId = friendUserId;
	}


}
