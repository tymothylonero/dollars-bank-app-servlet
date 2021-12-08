package com.dollarsbank.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			insertUser = conn.prepareStatement("INSERT INTO user");
			getUser = conn.prepareStatement("SELECT * FROM user WHERE username=?");
			insertTransaction = conn.prepareStatement("INSERT INTO transaction");
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
		String depositAmount = request.getParameter("deposit");
		
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");
		
		try {
			
			if(userExists(username)) {
				pw.println(PrintUtility.getPageStart(false));
				pw.println("<h1>Username already exists! Try another one.</h1>");
				pw.println(PrintUtility.getPageEnd(false));
				return;
			}
			pw.println("<h1>Success!</h1>");
			
		} catch (SQLException e) {
			pw.println(PrintUtility.getPageStart(false));
			pw.println("<h1>Error with SQL connection, cannot retrieve information at this time.</h1>");
			pw.println("<h4>" + e.toString() + "</h4>");
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

}
