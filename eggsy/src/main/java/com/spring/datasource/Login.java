package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class Login extends CommonBean {

	@JsonProperty
	private int loginTypeId;
	
	@JsonProperty
	private String loginUserName;
	
	@JsonProperty
	private String loginUserPassword;

	public int getLoginTypeId() {
		return loginTypeId;
	}

	public void setLoginTypeId(int loginTypeId) {
		this.loginTypeId = loginTypeId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginUserPassword() {
		return loginUserPassword;
	}

	public void setLoginUserPassword(String loginUserPassword) {
		this.loginUserPassword = loginUserPassword;
	}

}
