package com.spring.datasource;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lesson")
public class Lesson {
	private long id;
	private String name;
	private String description;
	private long isDeleted;
	private String createdBy;
	private String lastUpdatedBy;
	private Date createDate;
	private Date lastUpdateDate;
	private String eventDate;
	private String eventTime;
	private long lessonTypeId;
	private long userId;
	private String username;
	private String sessionId;
	private long isLive;
	private int rating;

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

	public long getLessonTypeId() {
		return lessonTypeId;
	}

	@XmlElement
	public void setLessonTypeId(long lessonTypeId) {
		this.lessonTypeId = lessonTypeId;
	}

	public long getUserId() {
		return userId;
	}

	@XmlElement
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	public long getIsLive() {
		return isLive;
	}

	public void setIsLive(long isLive) {
		this.isLive = isLive;
	}
	public long getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
