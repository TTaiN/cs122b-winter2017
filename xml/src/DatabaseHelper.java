/*
 * Example Usage:
	 * DatabaseHelper db = new DatabaseHelper(); // connects for you automatically
	 * ResultSet results = db.executeSQL("SELECT * FROM stars");
	 * if (results == null) // no results
	 * 		doSomething1(); // do something
	 * else doSomething2(); // do something else
	 * db.close(); // disconnects
 */

import java.sql.*;

public class DatabaseHelper 
{
	Connection connection;

	final static String ip = "localhost";//"35.167.240.46";
 	final static String user = "testuser";
    final static String password = "testpass";
    final static String database = "moviedb";
     
	
	public DatabaseHelper() throws SQLException
	{
		openConnection();
	}
	
	public PreparedStatement prepareStatement(String SQL) throws SQLException
	{
		return connection.prepareStatement(SQL);
	}
	
	public void commit() throws SQLException
	{
		connection.commit();
	}
	
	public void setAutoCommit(boolean value) throws SQLException
	{
		connection.setAutoCommit(value);
	}
	
	public boolean toggleAutoCommit(boolean value) throws SQLException
	{
		if (connection.getAutoCommit())
		{
			connection.setAutoCommit(false);
			return false;
		}
		else 
		{
			connection.setAutoCommit(true);
			return true;
		}
	}
	
	public ResultSet executeSQL(String SQL) throws SQLException
	{
		ResultSet result = null;
		Statement newStatement = connection.createStatement();
		result = newStatement.executeQuery(SQL);
		return result;
	}
	
	public void openConnection() throws SQLException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance(); //newInstance()?
		}
		catch (ClassNotFoundException e) // highly unlikely but could still happen.
		{
			e.printStackTrace();
		} catch (InstantiationException e)  // highly unlikely but could still happen.
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)  // highly unlikely but could still happen.
		{
			e.printStackTrace();
		}
		connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database, user, password);
	}
	
	public void closeConnection() throws SQLException
	{
		connection.close();
	}
}
