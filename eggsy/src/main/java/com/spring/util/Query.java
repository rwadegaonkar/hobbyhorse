package com.spring.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {
	public ResultSet executeQuery(String cql, Connection conn) {
		System.out.println(cql + "***********");
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(cql);
			return res;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public void executeUpdate(String cql, Connection conn) {
		// TODO Auto-generated method stub
		System.out.println(cql + "***********");
		Statement stmt;
		try {
			stmt = conn.createStatement();
			int res = stmt.executeUpdate(cql);
			System.out.println(res);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
