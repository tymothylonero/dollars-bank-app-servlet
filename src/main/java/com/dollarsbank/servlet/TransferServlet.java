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

public class TransferServlet extends HttpServlet {
	
	// Servlet which allows users to transfer funds from their account to another account
	
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	private PreparedStatement getUser;
	private PreparedStatement getReceiver;
	private PreparedStatement editBalance;
	private PreparedStatement insertTransaction;
	private PreparedStatement editReceiverBalance;
	private PreparedStatement insertReceiverTransaction;

	public void init(ServletConfig config) throws ServletException {
		
		conn = ConnectionManager.getConnection();
		try {
			getUser = conn.prepareStatement("SELECT * FROM user WHERE id=?");
			getReceiver = conn.prepareStatement("SELECT * FROM user WHERE username=?");
			editBalance = conn.prepareStatement("UPDATE user SET balance=? WHERE id=?");
			insertTransaction = conn.prepareStatement("INSERT INTO transaction(`type`, `description`, `amount`, `user_id`, `timestamp`) VALUES(?, ?, ?, ?, ?);");
			editReceiverBalance = conn.prepareStatement("UPDATE user SET balance=? WHERE id=?");
			insertReceiverTransaction = conn.prepareStatement("INSERT INTO transaction(`type`, `description`, `amount`, `user_id`, `timestamp`) VALUES(?, ?, ?, ?, ?);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {		
			getUser.close();
			getReceiver.close();
			editBalance.close();
			insertTransaction.close();
			editReceiverBalance.close();
			insertReceiverTransaction.close();
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
			String toAccountUsername = request.getParameter("toAccount");
			getUser.setString(1, id);
			ResultSet user = getUser.executeQuery();
			
			if(user.next()) {
				
				// Get the account to transfer to
				getReceiver.setString(1, toAccountUsername);
				ResultSet toUser = getReceiver.executeQuery();
				
				if(toUser.next()) {
					
					String amount = request.getParameter("transfer");
					
					// Change the user's balance
					double currentBalance = user.getDouble("balance");
					currentBalance -= Double.parseDouble(amount);
					editBalance.setString(1, currentBalance + "");
					editBalance.setString(2, id);
					
					int result = editBalance.executeUpdate();
					if(result == 0)
						throw new SQLException();
					
					// Change the receiver's balance
					double receiverBalance = toUser.getDouble("balance");
					receiverBalance += Double.parseDouble(amount);
					editReceiverBalance.setString(1, receiverBalance + "");
					editReceiverBalance.setString(2, toUser.getString("id"));
					
					result = editReceiverBalance.executeUpdate();
					if(result == 0)
						throw new SQLException();
					
					// Insert a new transaction for the user
					String timestamp = new Date().toString();
					
					insertTransaction.setString(1, "Funds Transfer");
					insertTransaction.setString(2, "Sent $" + amount + " to " + toUser.getString("username") + ": " + request.getParameter("description"));
					insertTransaction.setString(3, amount);
					insertTransaction.setString(4, id);
					insertTransaction.setString(5, timestamp);
					
					result = insertTransaction.executeUpdate();
					if(result == 0)
						throw new SQLException();
					
					// Insert a new transaction for the receiver
					insertReceiverTransaction.setString(1, "Funds transfer");
					insertReceiverTransaction.setString(2, "Received $" + amount + " from " + user.getString("username") + ": " + request.getParameter("description"));
					insertReceiverTransaction.setString(3, amount);
					insertReceiverTransaction.setString(4, toUser.getString("id"));
					insertReceiverTransaction.setString(5, timestamp);
					
					result = insertReceiverTransaction.executeUpdate();
					if(result == 0)
						throw new SQLException();
					
					// Return to the home menu with success alert
					pw.println(PrintUtility.getPageStart(true));
					pw.println(PrintUtility.getAlert("Successfully transfered $" + request.getParameter("transfer") + " to " + request.getParameter("toAccount") + ".", "alert-success"));
					pw.println(PrintUtility.getHomePage(user.getInt("id"), user.getString("username"), currentBalance, user.getString("email"), user.getString("address")));
					pw.println(PrintUtility.getPageEnd(true));
					
				} else {
					// Return but with an 'account does not exist' alert
					pw.println(PrintUtility.getPageStart(true));
					pw.println(PrintUtility.getAlert("Account name '" + toAccountUsername + "' does not exist.", "alert-danger"));
					pw.println(PrintUtility.getHomePage(user.getInt("id"), user.getString("username"), user.getDouble("balance"), user.getString("email"), user.getString("address")));
					pw.println(PrintUtility.getPageEnd(true));
				}
				
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