package ecommerce_helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import general_helpers.DatabaseHelper;

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
	
	private boolean nullCheck(ArrayList<String> messages)
	{
		if (this.number.equals(""))
		{
			messages.add("[ERROR] The Credit Card Number was not entered.");
		}
		if (this.firstName.equals(""))
		{
			messages.add("[ERROR] The Credit Card First Name was not entered.");

		}
		if (this.lastName.equals(""))
		{
			messages.add("[ERROR] The Credit Card Last Name was not entered.");
		}
		if (this.date.equals(""))
		{
			messages.add("[ERROR] The Credit Card Expiration Date was not entered.");
		}
		
		return messages.isEmpty();
	}
	
	public boolean validateInfo(ArrayList<String> messages) throws SQLException
	{
		
		if (!nullCheck(messages))
		{
			return false;
		}
		
		ResultSet results = db.executePreparedStatement("SELECT * FROM creditcards WHERE id = '" + this.number + "'");
				//"' AND first_name = '" + this.firstName + "' AND last_name = '" + this.lastName + "' AND expiration = '" + this.date + "'");
		
		if (results.next())
		{
			if (!results.getString("first_name").equals(this.firstName))
			{
				messages.add("[ERROR] The First Name (" + this.firstName + ") did not the match the first name for the Credit Card Number (" + this.number + ") in our records.");
			}
			if (!results.getString("last_name").equals(this.firstName))
			{
				messages.add("[ERROR] The Last Name (" + this.lastName + ") did not the match the last name for the Credit Card Number (" + this.number + ") in our records.");
			}
			if (!results.getString("expiration").equals(this.date))
			{
				messages.add("[ERROR] The Expiration Date (" + this.date + ") did not the match the expiration date for the Credit Card Number (" + this.number + ") in our records.");
			}
		}
		else
		{
			messages.add("[ERROR] The Credit Card Number (" + this.number + ") was not found in our records.");
		}
		
		return messages.isEmpty();
	}
	
	public void close() throws SQLException
	{
		db.closeConnection();
	}
}
