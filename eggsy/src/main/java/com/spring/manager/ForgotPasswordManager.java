package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.ForgotPasswordDao;
import com.spring.datasource.ForgotPassword;

public class ForgotPasswordManager {
	@JsonIgnore
	private static final Logger logger = Logger
			.getLogger(ForgotPasswordManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	ForgotPasswordDao delegate = new ForgotPasswordDao();
	private ArrayList<ForgotPassword> forgotpasswords = new ArrayList<ForgotPassword>();

	public ArrayList<ForgotPassword> getAllForgotPasswords() {
		try {
			forgotpasswords = delegate.getAllForgotPasswords(conn);
			return forgotpasswords;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
