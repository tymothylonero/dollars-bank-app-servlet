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

public class LoggedInServlet extends HttpServlet {
	
	// Servlet which allows users to log into their DollarsBank account

	private static final long serialVersionUID = 1L;

	private Connection conn;
	private PreparedStatement pstmt;

	public void init(ServletConfig config) throws ServletException {

		conn = ConnectionManager.getConnection();
		try {
			pstmt = conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void destroy() {
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get user input
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");

		try {

			// Find the given credentials in the MySQL database
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				// Return the logged-in home page
				pw.println(PrintUtility.getPageStart(true));
				pw.println(PrintUtility.getHomePage(rs.getInt("id"), rs.getString("username"), rs.getDouble("balance"), rs.getString("email"), rs.getString("address")));
				pw.println(PrintUtility.getPageEnd(true));
				
			} else {
				// Return home page with invalid credentials alert
				pw.println(PrintUtility.getPageStart(false));
				pw.println(PrintUtility.getAlert("Invalid credentials! Try again.", "alert-warning"));
				pw.println(PrintUtility.getWelcomePage());
				pw.println(PrintUtility.getPageEnd(false));
			}

			rs.close();

		} catch (SQLException e) {
			// Error with MySQL server
			pw.println(PrintUtility.returnError("Error with SQL connection, cannot retrieve information at this time."));

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
