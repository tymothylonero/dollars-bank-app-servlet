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

public class DepositServlet extends HttpServlet {
	
	// Servlet which allows users to make deposits into their accounts
	
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	private PreparedStatement getUser;
	private PreparedStatement editBalance;
	private PreparedStatement insertTransaction;

	public void init(ServletConfig config) throws ServletException {
		
		conn = ConnectionManager.getConnection();
		try {
			getUser = conn.prepareStatement("SELECT * FROM user WHERE id=?");
			editBalance = conn.prepareStatement("UPDATE user SET balance=? WHERE id=?");
			insertTransaction = conn.prepareStatement("INSERT INTO transaction(`type`, `description`, `amount`, `user_id`, `timestamp`) VALUES(?, ?, ?, ?, ?);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {		
			getUser.close();
			editBalance.close();
			insertTransaction.close();
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
				
				// Add funds to the user's balance
				double currentBalance = user.getDouble("balance");
				currentBalance += Double.parseDouble(request.getParameter("deposit"));
				editBalance.setString(1, currentBalance + "");
				editBalance.setString(2, id);
				
				int result = editBalance.executeUpdate();
				if(result == 0)
					throw new SQLException();
				
				// Create a new deposit transaction for the user
				insertTransaction.setString(1, "Deposit");
				insertTransaction.setString(2, request.getParameter("description"));
				insertTransaction.setString(3, request.getParameter("deposit"));
				insertTransaction.setString(4, id);
				insertTransaction.setString(5, new Date().toString());
				
				result = insertTransaction.executeUpdate();
				if(result == 0)
					throw new SQLException();
				
				// Return to the home page with a success alert
				pw.println(PrintUtility.getPageStart(true));
				pw.println(PrintUtility.getAlert("Successfully deposited $" + request.getParameter("deposit") + ".", "alert-success"));
				pw.println(PrintUtility.getHomePage(user.getInt("id"), user.getString("username"), currentBalance, user.getString("email"), user.getString("address")));
				pw.println(PrintUtility.getPageEnd(true));
				
			} else {
				throw new SQLException();
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