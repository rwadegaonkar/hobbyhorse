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
	private long lessonTypeId;

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

}
