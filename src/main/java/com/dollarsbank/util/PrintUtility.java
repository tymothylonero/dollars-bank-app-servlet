package com.dollarsbank.util;

public class PrintUtility {
	
	private static String basePage = "<!DOCTYPE html>\r\n"
			+ "<html>\r\n"
			+ "<head>\r\n"
			+ "<meta charset=\"ISO-8859-1\">\r\n"
			+ "<title>DollarsBank</title>\r\n"
			+ "\r\n"
			+ "<link\r\n"
			+ "	href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css\"\r\n"
			+ "	rel=\"stylesheet\"\r\n"
			+ "	integrity=\"sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU\"\r\n"
			+ "	crossorigin=\"anonymous\">\r\n"
			+ "\r\n"
			+ "</head>\n"
			+ "<body>";
	
	private static String baseEnd = "<script\r\n"
			+ "src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js\"\r\n"
			+ "integrity=\"sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ\"\r\n"
			+ "crossorigin=\"anonymous\"></script>\r\n"
			+ "\r\n"
			+ "</body>\r\n"
			+ "</html>";
	
	private static String loggedOutNav = "	<div class=\"collapse\" id=\"navbarToggleExternalContent\">\r\n"
			+ "	  <div class=\"bg-dark p-4\">\r\n"
			+ "	    <h5 class=\"text-white h4\">Welcome to Dollars Bank</h5>\r\n"
			+ "	    <p class=\"text-muted\">Email: dollarsbank@gmail.com</p>\r\n"
			+ "	    <p class=\"text-muted\">Phone: +1 (800) 123-4567</p>\r\n"
			+ "	    <p class=\"text-muted\">Copyright © Dollars Bank 2021</p>\r\n"
			+ "	  </div>\r\n"
			+ "	</div>\r\n"
			+ "\r\n"
			+ "	<nav class=\"navbar navbar-light\" style=\"background-color: #e3f2fd;\">\r\n"
			+ "		<div class=\"container-fluid\">\r\n"
			+ "			<button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarToggleExternalContent\" aria-controls=\"navbarToggleExternalContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n"
			+ "      			<span class=\"navbar-toggler-icon\"></span>\r\n"
			+ "    		</button>\r\n"
			+ "			<a class=\"navbar-brand\">Dollars Bank</a>\r\n"
			+ "			<form class=\"d-flex\">\r\n"
			+ "				<button class=\"btn btn-success\" type=\"button\" data-bs-toggle=\"modal\" data-bs-target=\"#signupModal\">Sign Up</button>\r\n"
			+ "				<button class=\"btn btn-primary\" type=\"button\" data-bs-toggle=\"modal\" data-bs-target=\"#loginModal\">Login</button>\r\n"
			+ "			</form>\r\n"
			+ "		</div>\r\n"
			+ "	</nav>";
	
	private static String loggedInNav = "	<div class=\"collapse\" id=\"navbarToggleExternalContent\">\r\n"
			+ "	  <div class=\"bg-dark p-4\">\r\n"
			+ "	    <h5 class=\"text-white h4\">Welcome to Dollars Bank</h5>\r\n"
			+ "	    <p class=\"text-muted\">Email: dollarsbank@gmail.com</p>\r\n"
			+ "	    <p class=\"text-muted\">Phone: +1 (800) 123-4567</p>\r\n"
			+ "	    <p class=\"text-muted\">Copyright © Dollars Bank 2021</p>\r\n"
			+ "	  </div>\r\n"
			+ "	</div>\r\n"
			+ "\r\n"
			+ "	<nav class=\"navbar navbar-light\" style=\"background-color: #e3f2fd;\">\r\n"
			+ "		<div class=\"container-fluid\">\r\n"
			+ "			<button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarToggleExternalContent\" aria-controls=\"navbarToggleExternalContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n"
			+ "      			<span class=\"navbar-toggler-icon\"></span>\r\n"
			+ "    		</button>\r\n"
			+ "			<a class=\"navbar-brand\">Dollars Bank</a>\r\n"
			+ "			<form class=\"d-flex\" action=\"HomePageServlet\" method=\"POST\">\r\n"
			+ "				<button class=\"btn btn-primary\" type=\"submit\">Log Out</button>\r\n"
			+ "			</form>\r\n"
			+ "		</div>\r\n"
			+ "	</nav>";
	
	private static String loggedOutModals = "	<!-- Login modal -->\r\n"
			+ "\r\n"
			+ "	<div class=\"modal fade\" id=\"loginModal\" data-bs-backdrop=\"static\"\r\n"
			+ "		data-bs-keyboard=\"false\" tabindex=\"-1\"\r\n"
			+ "		aria-labelledby=\"loginModalLabel\" aria-hidden=\"true\">\r\n"
			+ "		<div class=\"modal-dialog\">\r\n"
			+ "			<div class=\"modal-content\">\r\n"
			+ "			\r\n"
			+ "				<form action=\"LoggedInServlet\" method=\"POST\">\r\n"
			+ "			\r\n"
			+ "				<div class=\"modal-header\">\r\n"
			+ "					<h5 class=\"modal-title\" id=\"staticBackdropLabel\">Log into your Dollars Bank account</h5>\r\n"
			+ "					<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"\r\n"
			+ "						aria-label=\"Close\"></button>\r\n"
			+ "				</div>\r\n"
			+ "				<div class=\"modal-body\">\r\n"
			+ "					<p>Username:</p>\r\n"
			+ "					<input class=\"form-control me-2\" type=\"search\" aria-label=\"Username\" id=\"username\" name=\"username\" required>\r\n"
			+ "					<p></p>\r\n"
			+ "					<p>Password:</p>\r\n"
			+ "					<input class=\"form-control me-2\" type=\"password\" aria-label=\"Password\" id=\"password\" name=\"password\" required>\r\n"
			+ "				</div>\r\n"
			+ "				<div class=\"modal-footer\">\r\n"
			+ "					<button type=\"button\" class=\"btn btn-secondary\"\r\n"
			+ "						data-bs-dismiss=\"modal\">Cancel</button>\r\n"
			+ "					<button type=\"submit\" class=\"btn btn-primary\">Login</button>\r\n"
			+ "				</div>\r\n"
			+ "				\r\n"
			+ "				</form>\r\n"
			+ "				\r\n"
			+ "			</div>\r\n"
			+ "		</div>\r\n"
			+ "	</div>\r\n"
			+ "	\r\n"
			+ "	<!-- Signup modal -->\r\n"
			+ "\r\n"
			+ "	<div class=\"modal fade\" id=\"signupModal\" data-bs-backdrop=\"static\"\r\n"
			+ "		data-bs-keyboard=\"false\" tabindex=\"-1\"\r\n"
			+ "		aria-labelledby=\"signupModalLabel\" aria-hidden=\"true\">\r\n"
			+ "		<div class=\"modal-dialog\">\r\n"
			+ "			<div class=\"modal-content\">\r\n"
			+ "			\r\n"
			+ "				<form action=\"NewAccountServlet\" method=\"POST\">\r\n"
			+ "			\r\n"
			+ "				<div class=\"modal-header\">\r\n"
			+ "					<h5 class=\"modal-title\" id=\"staticBackdropLabel\">Sign Up for a Dollars Bank account</h5>\r\n"
			+ "					<button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"\r\n"
			+ "						aria-label=\"Close\"></button>\r\n"
			+ "				</div>\r\n"
			+ "				<div class=\"modal-body\">\r\n"
			+ "					<p>Create a username:</p>\r\n"
			+ "					<input class=\"form-control me-2\" type=\"search\" aria-label=\"Username\" id=\"username\" name=\"username\" required>\r\n"
			+ "					<p></p>\r\n"
			+ "					<p>Password:</p>\r\n"
			+ "					<input class=\"form-control me-2\" type=\"password\" aria-label=\"Password\" id=\"password\" name=\"password\" required>\r\n"
			+ "					<p></p>\r\n"
			+ "					<p>Email:</p>\r\n"
			+ "					<input class=\"form-control me-2\" type=\"search\" aria-label=\"Email\" id=\"email\" name=\"email\" required>\r\n"
			+ "					<p></p>\r\n"
			+ "					<p>Address:</p>\r\n"
			+ "					<input class=\"form-control me-2\" type=\"search\" aria-label=\"Address\" id=\"address\" name=\"address\" required>\r\n"
			+ "					<p></p>\r\n"
			+ "					<p>Amount to deposit initially:</p>\r\n"
			+ "					<div class=\"input-group mb-3\">\r\n"
			+ "	  					<span class=\"input-group-text\">$</span>\r\n"
			+ "	  					<input type=\"text\" class=\"form-control\" aria-label=\"Initial deposit amount\" id=\"deposit\" name=\"deposit\">\r\n"
			+ "					</div>\r\n"
			+ "				</div>\r\n"
			+ "				<div class=\"modal-footer\">\r\n"
			+ "					<button type=\"button\" class=\"btn btn-secondary\"\r\n"
			+ "						data-bs-dismiss=\"modal\">Cancel</button>\r\n"
			+ "					<button type=\"submit\" class=\"btn btn-success\">Create Account</button>\r\n"
			+ "				</div>\r\n"
			+ "				\r\n"
			+ "				</form>\r\n"
			+ "				\r\n"
			+ "			</div>\r\n"
			+ "		</div>\r\n"
			+ "	</div>";
	
	private static String loggedInModals = "";
	
	private static String homeMenu = "	<div style=\"text-align:center;\">\r\n"
			+ "		<br>\r\n"
			+ "		<br>\r\n"
			+ "		<br>\r\n"
			+ "		<h1>What would you like to do today?</h1>\r\n"
			+ "		<br>\r\n"
			+ "		<br>\r\n"
			+ "		<br>\r\n"
			+ "		<div><button class=\"btn btn-primary\" type=\"button\">Deposit</button></div>\r\n"
			+ "		<br>\r\n"
			+ "		<div><button class=\"btn btn-primary\" type=\"button\">Withdraw</button></div>\r\n"
			+ "		<br>\r\n"
			+ "		<div><button class=\"btn btn-primary\" type=\"button\">Transfer Funds</button></div>\r\n"
			+ "		<br>\r\n"
			+ "		<div><button class=\"btn btn-primary\" type=\"button\">Recent Transactions</button></div>\r\n"
			+ "		<br>\r\n"
			+ "		<div><button class=\"btn btn-primary\" type=\"button\">Account Information</button></div>\r\n"
			+ "		<br>\r\n"
			+ "		<div><button class=\"btn btn-primary\" type=\"button\">Change Password</button></div>\r\n"
			+ "	</div>";
	
	public static String getPageStart(boolean loggedIn) {
		
		if(loggedIn) {
			return basePage + loggedInNav;
		} else {
			return basePage + loggedOutNav;
		}
		
	}
	
	public static String getPageEnd(boolean loggedIn) {
		
		if(loggedIn) {
			return loggedInModals + baseEnd;
		} else {
			return loggedOutModals + baseEnd;
		}
	}
	
	public static String getHomePage(int id, String username, double balance, String email, String address) {
		return homeMenu;
	}

}