package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.LessonTypeDao;
import com.spring.datasource.CommonBean;

@XmlRootElement(name = "lessontypes")
public class LessonTypeManager {
	@JsonIgnore
	private static final Logger logger = Logger
			.getLogger(LessonTypeManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	LessonTypeDao delegate = new LessonTypeDao();
	private ArrayList<CommonBean> lessonTypes = new ArrayList<CommonBean>();

	public ArrayList<CommonBean> getLessonTypes() {
		return lessonTypes;
	}

	public void setLessonTypes(ArrayList<CommonBean> lessonTypes) {
		this.lessonTypes = lessonTypes;
	}

	public ArrayList<CommonBean> getAllLessonTypes() {
		try {
			lessonTypes = delegate.getAllLessonTypes(conn);
			return lessonTypes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
