package com.spring.manager;

import java.sql.Connection;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.spring.conf.ConnMysql;
import com.spring.dao.RoleDao;
import com.spring.datasource.CommonBean;

@XmlRootElement(name = "roles")
public class RoleManager {
	@JsonIgnore
	private static final Logger logger = Logger.getLogger(RoleManager.class);

	@JsonIgnore
	Connection conn = ConnMysql.connect();
	@JsonIgnore
	RoleDao delegate = new RoleDao();
	private ArrayList<CommonBean> roles = new ArrayList<CommonBean>();

	public ArrayList<CommonBean> getAllRoles() {
		try {
			roles = delegate.getAllRoles(conn);
			return roles;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
