package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class Login extends CommonBean {

	@JsonProperty
	private int loginTypeId;

	public int getLoginTypeId() {
		return loginTypeId;
	}

	public void setLoginTypeId(int loginTypeId) {
		this.loginTypeId = loginTypeId;
	}

}
