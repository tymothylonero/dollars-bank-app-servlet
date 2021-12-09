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

		//pw.println("<html>");
		//pw.println("<head> <title>DollarsBank</title> </head>");
		//pw.println("<body>");
		

		try {
			// Put user input into SQL statement
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			// Execute query
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				pw.println(PrintUtility.getPageStart(true));
				
//				pw.println("<h1>User found in database:</h1>");
//				String name = rs.getString("username");
//				String pass = rs.getString("password");
//				pw.println("<h3>" + name + "</h3>");
//				pw.println("<p>" + pass + "</p>");
				
				pw.println(PrintUtility.getHomePage(1,"",0.0,"",""));
				
				pw.println(PrintUtility.getPageEnd(true));
				
			} else {
				pw.println(PrintUtility.getPageStart(false));
				pw.println("<br><br><br><h1 style=\"text-align:center;\">Invalid credentials! Try again.</h1>");
				pw.println(PrintUtility.getPageEnd(false));
			}

			rs.close();

		} catch (SQLException e) {

			// SQL exception
			pw.println("<h1>Error with SQL connection, cannot retrieve information at this time.</h1>");

		}

		//pw.println("</body>");
		//pw.println("</html>");
		//pw.println("<script>alert(\"Could not find a user with those credentials. Try again.\")</script>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
