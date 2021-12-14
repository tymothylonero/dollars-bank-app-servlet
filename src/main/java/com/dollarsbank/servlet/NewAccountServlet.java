package com.dollarsbank.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dollarsbank.util.PrintUtility;

public class NewAccountServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Connection conn;
	private PreparedStatement userCheck;
	private PreparedStatement insertUser;
	private PreparedStatement getUser;
	private PreparedStatement insertTransaction;

	public void init(ServletConfig config) throws ServletException {

		conn = ConnectionManager.getConnection();
		try {
			userCheck = conn.prepareStatement("SELECT * FROM user WHERE username=?");
			insertUser = conn.prepareStatement("INSERT INTO user (`username`, `password`, `email`, `address`, `balance`) VALUES (?, ?, ?, ?, ?);");
			getUser = conn.prepareStatement("SELECT * FROM user WHERE username=?");
			insertTransaction = conn.prepareStatement("INSERT INTO transaction(`type`, `description`, `amount`, `user_id`, `timestamp`) VALUES(?, ?, ?, ?, ?);");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void destroy() {
		try {
			userCheck.close();
			insertUser.close();
			getUser.close();
			insertTransaction.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get user input
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		Double depositAmount;
		if(request.getParameter("deposit").equals("")) {
			depositAmount = 0.0;
		} else {
			depositAmount = Double.parseDouble(request.getParameter("deposit"));
		}
		
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");
		
		try {
			
			if(userExists(username)) {
				pw.println(PrintUtility.getPageStart(false));
				pw.println(PrintUtility.getErrorAlert("Username already exists! Try another one."));
				pw.println(PrintUtility.getPageEnd(false));
				return;
			}
			
			ResultSet newUser = insertUser(username, password, email, address, depositAmount);
			
			if(newUser.next()) {
				
				if(depositAmount > 0) {
					
					insertTransaction.setString(1, "Deposit");
					insertTransaction.setString(2, "Initial Deposit");
					insertTransaction.setString(3, newUser.getString("balance"));
					insertTransaction.setString(4, newUser.getString("id"));
					insertTransaction.setString(5, new Date().toString());
					
					int result = insertTransaction.executeUpdate();
					if(result == 0)
						throw new SQLException();
					
				}
				
				pw.println(PrintUtility.getPageStart(true));
				pw.println(PrintUtility.getHomePage(newUser.getInt("id"), newUser.getString("username"), newUser.getDouble("balance"), newUser.getString("email"), newUser.getString("address")));
				pw.println(PrintUtility.getPageEnd(true));
			} else {
				throw new SQLException();
			}
			
		} catch (SQLException e) {
			pw.println(PrintUtility.getPageStart(false));
			pw.println("<h1>Error with SQL connection, cannot retrieve information at this time.</h1>");
			pw.println(PrintUtility.getPageEnd(false));
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private boolean userExists(String username) throws SQLException {
		
		userCheck.setString(1, username);
		
		ResultSet rs = userCheck.executeQuery();
		if(rs.next()) {
			rs.close();
			return true;
		} else {
			rs.close();
			return false;
		}
	}
	
	private ResultSet insertUser(String username, String password, String email, String address, Double deposit) throws SQLException {
		
		insertUser.setString(1, username);
		insertUser.setString(2, password);
		insertUser.setString(3, email);
		insertUser.setString(4, address);
		insertUser.setDouble(5, deposit);
		
		int result = insertUser.executeUpdate();
		
		if(result == 0)
			throw new SQLException();
		
		getUser.setString(1, username);
		ResultSet rs = getUser.executeQuery();
		
		return rs;
	}

}
