package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.BadgeDao;
import com.spring.datasource.Badge;

public class BadgeManager {
	@JsonIgnore
	private static final Logger logger = Logger.getLogger(BadgeManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	BadgeDao delegate = new BadgeDao();
	private ArrayList<Badge> badges = new ArrayList<Badge>();

	public ArrayList<Badge> getAllBadges() {
		try {
			badges = delegate.getAllBadges(conn);
			return badges;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
