package com.dollarsbank.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static final String URL = "jdbc:mysql://localhost:3306/dollars_bank_v1";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	public static Connection getConnection() {
		
		// Make a connection to the MySQL database
		
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
			System.out.println("Succesfully made the connection.");
			
		} catch(SQLException e) {
			System.out.println("Exception: Couldn't make the connection!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
}