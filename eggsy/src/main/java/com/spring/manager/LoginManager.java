package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.LoginDao;
import com.spring.datasource.Login;
import com.spring.datasource.User;

public class LoginManager {
	@JsonIgnore
	private static final Logger logger = Logger
			.getLogger(LoginManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	LoginDao delegate = new LoginDao();
	private ArrayList<Login> logins = new ArrayList<Login>();

	public ArrayList<Login> getAllLogins() {
		try {
			logins = delegate.getAllLogins(conn);
			return logins;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
