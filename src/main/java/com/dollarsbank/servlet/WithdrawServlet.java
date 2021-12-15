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

public class WithdrawServlet extends HttpServlet {
	
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
			
			String id = request.getParameter("id");
			getUser.setString(1, id);
			ResultSet user = getUser.executeQuery();
			
			if(user.next()) {
				
				double currentBalance = user.getDouble("balance");
				currentBalance -= Double.parseDouble(request.getParameter("withdraw"));
				editBalance.setString(1, currentBalance + "");
				editBalance.setString(2, id);
				
				int result = editBalance.executeUpdate();
				if(result == 0)
					throw new SQLException();
				
				insertTransaction.setString(1, "Withdraw");
				insertTransaction.setString(2, request.getParameter("description"));
				insertTransaction.setString(3, request.getParameter("withdraw"));
				insertTransaction.setString(4, id);
				insertTransaction.setString(5, new Date().toString());
				
				result = insertTransaction.executeUpdate();
				if(result == 0)
					throw new SQLException();
				
				pw.println(PrintUtility.getPageStart(true));
				pw.println(PrintUtility.getAlert("Successfully withdrew $" + request.getParameter("withdraw") + ".", "alert-success"));
				pw.println(PrintUtility.getHomePage(user.getInt("id"), user.getString("username"), currentBalance, user.getString("email"), user.getString("address")));
				pw.println(PrintUtility.getPageEnd(true));
				
			} else {
				throw new SQLException();
			}
			
			
		} catch (Exception e) {
			pw.println(PrintUtility.returnError("Error with SQL connection, cannot retrieve information at this time."));
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}