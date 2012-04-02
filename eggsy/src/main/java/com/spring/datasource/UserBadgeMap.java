package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserBadgeMap extends CommonBean {
	@JsonProperty
	private int userId;	
	@JsonProperty
	private int badgeId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBadgeId() {
		return badgeId;
	}
	public void setBadgeId(int badgeId) {
		this.badgeId = badgeId;
	}
}
