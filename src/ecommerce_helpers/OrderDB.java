package ecommerce_helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import general_helpers.DatabaseHelper;
import ecommerce_helpers.ShoppingCart;

public class OrderDB 
{
	private DatabaseHelper db;
	
	private String number;
	private String firstName;
	private String lastName;
	private String date;
	
	public OrderDB(String number, String firstName, String lastName, String date) throws SQLException
	{
		this.number = number.trim();
		this.firstName = firstName.trim();
		this.lastName = lastName.trim();
		this.date = date.trim();
		db = new DatabaseHelper(true);
	}
	
	public String getDBNumber()
	{
		return this.number;
	}
	
	// Source: http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
	public static boolean numberCheck(String s) 
	{
		for (int index = 0; index < s.length(); index++)
		{
			if (s.charAt(index) != '-' && s.charAt(index) != ' ' && Character.digit(s.charAt(index), 10) < 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean dateCheck(String s) 
	{
		for (int index = 0; index < s.length(); index++)
		{
			if (s.charAt(index) != '-' && Character.digit(s.charAt(index), 10) < 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean nameCheck(String s) 
	{
		for (int index = 0; index < s.length(); index++)
		{
			if (s.charAt(index) != '.' && !Character.isLetter(s.charAt(index)))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean inputCheck(ArrayList<String> messages)
	{
		if (this.number.equals(""))
		{
			messages.add("[ERROR] The Credit Card Number was not entered.");
		}
		else if (!numberCheck(this.number))
		{
			messages.add("[ERROR] The Credit Card Number can only contain numbers, spaces, and dashes.");
		}
		
		if (this.firstName.equals(""))
		{
			messages.add("[ERROR] The Credit Card First Name was not entered.");
		}
		else if (!nameCheck(this.firstName))
		{
			messages.add("[ERROR] The Credit Card First Name can only contain letters and dots.");
		}
		
		if (this.lastName.equals(""))
		{
			messages.add("[ERROR] The Credit Card Last Name was not entered.");
		}
		else if (!nameCheck(this.lastName))
		{
			messages.add("[ERROR] The Credit Card Last Name can only contain letters and dots.");
		}
		
		if (this.date.equals(""))
		{
			messages.add("[ERROR] The Credit Card Expiration Date was not entered.");
		}
		else if (!dateCheck(this.date))
		{
			messages.add("[ERROR] The Credit Card Expiration Date can only contain numbers and dashes.");
		}
		
		return messages.isEmpty();
	}
	
	public boolean validateInfo(ArrayList<String> messages) throws SQLException
	{
		
		if (!inputCheck(messages))
		{
			return false;
		}
		
		Connection conn = db.getConnection();
	 	String query = "SELECT * FROM creditcards WHERE REPLACE(REPLACE(id, '-', ''), ' ', '') = REPLACE(REPLACE(?, '-', ''), ' ', '')";
	 	PreparedStatement statement = conn.prepareStatement(query);
	 	statement.setString(1, this.number);
	 	ResultSet results = statement.executeQuery();

		if (results.next())
		{
			if (!results.getString("first_name").toLowerCase().equals(this.firstName.toLowerCase()) || !results.getString("last_name").toLowerCase().equals(this.lastName.toLowerCase()) || !results.getString("expiration").equals(this.date))
			{
				messages.add("[ERROR] The information provided did not match any entries in our records.");
			}
		}
		else
		{
			messages.add("[ERROR] The information provided did not match any entries in our records.");
		}
		
		if (messages.isEmpty())
		{
			this.number = results.getString("id");
		}
		
		return messages.isEmpty();
	}
	
	public void submitOrders(Integer userId, ShoppingCart cart) throws SQLException
	{
		// Source: http://stackoverflow.com/questions/16413019/java-program-need-current-date-in-yyyy-mm-dd-format-without-time-in-date-datat
		Date date = new Date();
		String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		Connection conn = db.getConnection();
	 	String query = "INSERT INTO sales (customer_id, movie_id, sale_date) VALUES (?, ?, ?);";
	 	PreparedStatement statement = conn.prepareStatement(query);
		
		for (Integer movie : cart.getMovies())
		{
			for (int count = 0; count != cart.getMovie(movie).getQuantity(); count++)
			{
			 	statement.setInt(1, userId);
			 	statement.setInt(2, cart.getMovie(movie).getId());
			 	statement.setString(3, formattedDate);
				if (!(statement.executeUpdate() > 0)) 
				{
					throw new SQLException("An order was not submitted to the database correctly.");
				}
			}
		}
	}
	
	public void close() throws SQLException
	{
		db.closeConnection();
	}
}
