package com.spring.datasource;

import org.codehaus.jackson.annotate.JsonProperty;

public class AprioriLesson {
	@JsonProperty
	private int id;
	private String main;
	private String association;
	private String support;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getAssociation() {
		return association;
	}

	public void setAssociation(String association) {
		this.association = association;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

}
