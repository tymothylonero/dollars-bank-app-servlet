package com.dollarsbank.util;

import java.util.Date;

public class TransactionUtility {
	
	public static boolean insertTransaction(String type, String description, double amount, int user_id) {
		
		String timestamp = new Date().toString();
		return true;
	}
	
	public static String getFiveRecentTransactions(int accountId) {
		return "";
	}

}