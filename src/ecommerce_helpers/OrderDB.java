package ecommerce_helpers;

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
		this.number = number;
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = date;
		db = new DatabaseHelper();
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
		return s.matches("[a-zA-Z]+");
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
			messages.add("[ERROR] The Credit Card First Name can only contain letters.");
		}
		
		if (this.lastName.equals(""))
		{
			messages.add("[ERROR] The Credit Card Last Name was not entered.");
		}
		else if (!nameCheck(this.lastName))
		{
			messages.add("[ERROR] The Credit Card Last Name can only contain letters.");
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
		
		ResultSet results = db.executePreparedStatement("SELECT * FROM creditcards WHERE REPLACE(REPLACE(id, '-', ''), ' ', '') = REPLACE(REPLACE('" + this.number + "', '-', ''), ' ', '')");
				//"' AND first_name = '" + this.firstName + "' AND last_name = '" + this.lastName + "' AND expiration = '" + this.date + "'");
		
		if (results.next())
		{
			if (!results.getString("first_name").equals(this.firstName))
			{
				messages.add("[ERROR] The information provided did not match any entries in our records.");
				//messages.add("[ERROR] The First Name (" + this.firstName + ") did not the match the first name for the Credit Card Number (" + this.number + ") in our records.");
			}
			else if (!results.getString("last_name").equals(this.lastName)) // changed to else if for now
			{
				messages.add("[ERROR] The information provided did not match any entries in our records.");

				//messages.add("[ERROR] The Last Name (" + this.lastName + ") did not the match the last name for the Credit Card Number (" + this.number + ") in our records.");
			}
			else if (!results.getString("expiration").equals(this.date))
			{
				messages.add("[ERROR] The information provided did not match any entries in our records.");

				//messages.add("[ERROR] The Expiration Date (" + this.date + ") did not the match the expiration date for the Credit Card Number (" + this.number + ") in our records.");
			}
		}
		else
		{
			messages.add("[ERROR] The information provided did not match any entries in our records.");
			//messages.add("[ERROR] The Credit Card Number (" + this.number + ") was not found in our records.");
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
		String SQL;
		
		for (Integer movie : cart.getMovies())
		{
			for (int count = 0; count != cart.getMovie(movie).getQuantity(); count++)
			{
				SQL = "INSERT INTO sales (customer_id, movie_id, sale_date) VALUES ('" + userId + "', '" + cart.getMovie(movie).getId() + "', '" + formattedDate + "');";
				if (!(db.executeInsertPS(SQL) > 0))
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
