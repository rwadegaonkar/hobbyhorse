package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class ForgotPassword extends CommonBean {
	@JsonProperty
	private String question;
	@JsonProperty
	private String answer;
	@JsonProperty
	private int userId;
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
