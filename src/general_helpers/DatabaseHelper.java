package general_helpers;

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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseHelper 
{
	Connection connection;
	final static String ip = "35.167.240.46";
	final static String user = "cs122b";
    final static String password = "cs122bgroup42";
    final static String database = "moviedb";

	public DatabaseHelper(boolean master) throws SQLException
	{
		openConnection(getDataSource(master));
	}
	
	public Connection getConnection()
	{
		return connection;
	}
	
	public ResultSet executeSQL(String SQL) throws SQLException
	{
		ResultSet result = null;
		Statement newStatement = connection.createStatement();
		result = newStatement.executeQuery(SQL);
		return result;
	}
	
	public ResultSet executePreparedStatement(String SQL) throws SQLException // prefer to use this one than executeSQL, more secure
	{
		PreparedStatement statement = connection.prepareStatement(SQL);
		return statement.executeQuery();
	}
	
	public int executeInsertPS(String SQL) throws SQLException // prefer to use this one than executeSQL, more secure
	{
		PreparedStatement statement = connection.prepareStatement(SQL);
		return statement.executeUpdate();
	}
	
	public void openConnection(String dataSource) throws SQLException
	{
		
		/*
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
		connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database + "?autoReconnect=true&useSSL=false", user, password);
		*/
		// Connection Pooling
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Context initContext = new InitialContext();
			Context envContext  = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup(dataSource);
			connection = ds.getConnection();
		} 
		catch (NamingException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public String getDataSource(boolean master)
	{
		return master ? "jdbc/MovieDBMaster" : "jdbc/MovieDB";
	}
	
	public void closeConnection() throws SQLException
	{
		connection.close();
	}
}
