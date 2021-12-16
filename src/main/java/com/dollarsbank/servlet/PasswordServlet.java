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

public class PasswordServlet extends HttpServlet {
	
	// Servlet which allows a user to change the password on their account
	
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	private PreparedStatement getUser;
	private PreparedStatement changePassword;

	public void init(ServletConfig config) throws ServletException {
		
		conn = ConnectionManager.getConnection();
		try {
			getUser = conn.prepareStatement("SELECT * FROM user WHERE id=?");
			changePassword = conn.prepareStatement("UPDATE user SET password=? WHERE id=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			getUser.close();
			changePassword.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");
		
		try {
			// Get the current user's information
			String id = request.getParameter("id");
			getUser.setString(1, id);
			ResultSet user = getUser.executeQuery();
			
			if(user.next()) {
				
				String current = user.getString("password");
				String oldPassword = request.getParameter("oldpassword");
				String newPassword = request.getParameter("newpassword");
				
				// Check if the user can guess their old password
				if(current.equals(oldPassword)) {
					
					// Check if the user entered a new password
					if(!current.equals(newPassword)) {
	
						// Change the user's password
						changePassword.setString(1, newPassword);
						changePassword.setString(2, id);
						int result = changePassword.executeUpdate();
						
						if(result == 0)
							throw new SQLException();
						
						// Return the home page with a success alert
						pw.println(PrintUtility.getPageStart(true));
						pw.println(PrintUtility.getAlert("Successfully changed your password.", "alert-success"));
						pw.println(PrintUtility.getHomePage(user.getInt("id"), user.getString("username"), user.getDouble("balance"), user.getString("email"), user.getString("address")));
						pw.println(PrintUtility.getPageEnd(true));
						
					} else {
						
						// Old password and new password match: No change would occur
						pw.println(PrintUtility.getPageStart(true));
						pw.println(PrintUtility.getAlert("The new password matches the old password. No changes were made.", "alert-warning"));
						pw.println(PrintUtility.getHomePage(user.getInt("id"), user.getString("username"), user.getDouble("balance"), user.getString("email"), user.getString("address")));
						pw.println(PrintUtility.getPageEnd(true));
					}
					
				} else {
					
					// Error: User did not enter the correct password				
					pw.println(PrintUtility.getPageStart(true));
					pw.println(PrintUtility.getAlert("Incorrect current password. No changes were made.", "alert-danger"));
					pw.println(PrintUtility.getHomePage(user.getInt("id"), user.getString("username"), user.getDouble("balance"), user.getString("email"), user.getString("address")));
					pw.println(PrintUtility.getPageEnd(true));
				}
				
			}
			
		} catch (Exception e) {
			// Error with MySQL server
			pw.println(PrintUtility.returnError("Error with SQL connection, cannot retrieve information at this time."));
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}