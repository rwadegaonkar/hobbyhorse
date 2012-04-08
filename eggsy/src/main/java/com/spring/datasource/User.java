package com.spring.datasource;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
	private long id;
	private String name;
	private String description;
	private long isDeleted;
	private String createdBy;
	private String lastUpdatedBy;
	private Date createDate;
	private Date lastUpdateDate;
	private String username;
	private String email;
	private String skills;
	private String hobbies;
	private String location;

	public long getId() {
		return id;
	}

	@XmlElement
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	public long getIsDeleted() {
		return isDeleted;
	}

	@XmlElement
	public void setIsDeleted(long isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	@XmlElement
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	@XmlElement
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	@XmlElement
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	@XmlElement
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getUsername() {
		return username;
	}

	@XmlElement
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkills() {
		return skills;
	}

	@XmlElement
	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getHobbies() {
		return hobbies;
	}

	@XmlElement
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getLocation() {
		return location;
	}

	@XmlElement
	public void setLocation(String location) {
		this.location = location;
	}
}
