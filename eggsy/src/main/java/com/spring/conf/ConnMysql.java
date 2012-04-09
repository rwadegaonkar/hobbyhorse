package com.spring.conf;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnMysql {
	private static Connection conn = null;
	private static String dbUrl = "jdbc:mysql://localhost:8889/hobbyhorse";
	private static String userName = "root";
	private static String password = "root";

	public static Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl,userName,password);
			System.out.println("Connected to the database");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void disconnect(Connection conn) {
		try {
			conn.close();
			System.out.println("Disconnected from database");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void destructor() {
		disconnect(conn);
	}
}
