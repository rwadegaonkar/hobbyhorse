package com.spring.datasource;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect
public class CommonBean {
	@JsonProperty
	private long id;
	@JsonProperty
	private String name;
	@JsonProperty
	private String description;
	@JsonProperty
	private long isDeleted;
	@JsonProperty
	private String createdBy;
	@JsonProperty
	private String lastUpdatedBy;
	@JsonProperty
	private Date createDate;
	@JsonProperty
	private Date lastUpdateDate;

	@JsonProperty
	public long getId() {
		return id;
	}

	@JsonProperty
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(long isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}
