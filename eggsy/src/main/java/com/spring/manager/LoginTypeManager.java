package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.LoginTypeDao;
import com.spring.datasource.CommonBean;

public class LoginTypeManager {
	@JsonIgnore
	private static final Logger logger = Logger.getLogger(LoginTypeManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	LoginTypeDao delegate = new LoginTypeDao();
	private ArrayList<CommonBean> loginTypes = new ArrayList<CommonBean>();

	public ArrayList<CommonBean> getAllLoginTypes() {
		try {
			loginTypes = delegate.getAllLoginTypes(conn);
			return loginTypes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
