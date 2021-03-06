package com.dollarsbank.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dollarsbank.servlet.ConnectionManager;

public class TransactionUtility {
	
	private static Connection conn;
	private static PreparedStatement getTransactions;
	
	// Return five recent transactions with the account
	public static String getFiveRecentTransactions(int accountId) {
		
		String html = "";
		
		try {
			// Get the transactions from the MySQL server
			conn = ConnectionManager.getConnection();
			getTransactions = conn.prepareStatement("SELECT * FROM transaction WHERE user_id=? ORDER BY id DESC LIMIT 5");
			
			getTransactions.setInt(1, accountId);
			
			ResultSet trans = getTransactions.executeQuery();
			
			// Create the HTML which displays the recent transactions
			while(trans.next()) {
				html += "<h5>" + trans.getString("type") + "</h5>";
				html += "<p>Amount: $" + trans.getString("amount") + "</p>";
				String desc = trans.getString("description");
				if(desc == null) {
					html += "<p>No description was given.</p>";
				} else {
					html += "<p>" + trans.getString("description") + "</p>";
				}
				html += "<p>Occured on: " + trans.getString("timestamp") + "</p><br>";
			}
			
			conn.close();
			getTransactions.close();
			
		} catch (Exception e) {
			// Error with MySQL server
			html = "Error with SQL connection, cannot retrieve information at this time.";
		}
		
		return html;
	}

}