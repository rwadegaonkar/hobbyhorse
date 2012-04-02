package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class Redeemption extends CommonBean {
	@JsonProperty
	private int badgeId;

	public int getBadgeId() {
		return badgeId;
	}

	public void setBadgeId(int badgeId) {
		this.badgeId = badgeId;
	}

}
