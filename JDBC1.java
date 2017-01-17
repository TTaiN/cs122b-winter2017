// JDBC Example - printing a database's metadata
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson

import java.sql.*; // Enable SQL processing
import java.util.Scanner;

public class JDBC1
{
       public static void main(String[] arg) throws Exception
       {
           // Incorporate mySQL driver
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           
           Scanner s = new Scanner(System.in);
           Connection connection;
           String username, password;
           
    	   System.out.println("Welcome to the database access program. Please enter the database credentials.");
    	   
    	   /* Begin Credentials */
    	   while(true)
    	   {
    		   System.out.print("Username: ");
    		   username = s.nextLine();
    		   System.out.print("Password: ");
    		   password = s.nextLine();
    		   System.out.println("\nConnecting...\n");
    		   	   
    		   try
    		   {
    			   connection = DriverManager.getConnection("jdbc:mysql://35.167.240.46/moviedb?autoReconnect=true&useSSL=false", username, password);
    		   }
    		   catch (Exception e) 
    		   {
    			   System.out.println(e.getCause()); 
    			   continue;
    		   }
        	   System.out.println("Access granted. Showing menu...\n");
        	   break;
    	   }
    	   
    	   /* Begin Menu */
           while(true) 
	       {
		       try
		       {
			       printMenu();
			       String input = s.nextLine();
			       if (input.equals("1")) 
				       printMovie(connection);
			       
			       else if (input.equals("2")) 
				       insert_new_star(connection);
			       
			       else if (input.equals("3")) 
				       insert_new_customer(connection);
				       
			       else if (input.equals("4")) 
				       delete_customer(connection);
			       
			       else if (input.equals("5")) 
				       print_metadata(connection);
			       
			       else if (input.equals("6"))
				       run_sql_command(connection);
				       
			       else if (input.equals("7")) 
			       {
			    	   System.out.println("Goodbye!");
				       break;
			       }
			       else 
				       System.out.println("Invalid command");  
		       }
		       catch( SQLException e)
		       {
			       System.out.println(e.getMessage());
		       }
	       }
           s.close();               
       }
       
       public static void printMenu() 
       {
    	   System.out.println("Select a menu:");
    	   System.out.println("1: Print movie featuring a given star");
    	   System.out.println("2: Insert new star");
    	   System.out.println("3: Insert new customer");
    	   System.out.println("4: Delete customer");
    	   System.out.println("5: Provide metadata");
    	   System.out.println("6: Enter SQL command");
    	   System.out.println("7: Exit menu");
       }
       
       public static  void printMovie(Connection connection) throws SQLException
       {
    	   Statement select = connection.createStatement();
    	   Scanner s = new Scanner(System.in);
           System.out.println("Enter a N to look up by name or I to look up by id");
           String input = s.nextLine();
           String id;
           String firstName;
           String lastName;
           ResultSet result = null;
           
           if (input.equalsIgnoreCase("I")) {
        	   System.out.println("Enter id");
        	   id = s.nextLine();
        	   result = select.executeQuery("Select m.* from movies m inner join stars_in_movies sim on m.id = sim.movie_id where sim.star_id ='" + id + "'");
           } else if (input.equalsIgnoreCase("N")) {
        	   System.out.println("A)Search by first AND last name. B)Search by first name. C) Search by last name.");
        	   String choice = s.nextLine();
        	   if (choice.equalsIgnoreCase("A")) {
        		   System.out.println("Enter first name");
        		   firstName = s.nextLine();
        		   System.out.println("Enter last name");
        		   lastName = s.nextLine();
        		   result = select.executeQuery("Select m.* from movies m inner join stars_in_movies sim on m.id = sim.movie_id inner join stars s on s.id = sim.star_id where s.first_name ='" + firstName + "' and s.last_name ='" + lastName + "'");
        	   } else if (choice.equalsIgnoreCase("B")) {
        		   System.out.println("Enter first name");
        		   firstName = s.nextLine();
        		   result = select.executeQuery("Select m.* from movies m inner join stars_in_movies sim on m.id = sim.movie_id inner join stars s on s.id = sim.star_id where s.first_name ='" + firstName + "'");
        	   } else if (choice.equalsIgnoreCase("C")) {
        		   System.out.println("Enter last name");
        		   lastName = s.nextLine();
        		   result = select.executeQuery("Select m.* from movies m inner join stars_in_movies sim on m.id = sim.movie_id inner join stars s on s.id = sim.star_id where s.last_name ='" + lastName + "'");
        	   } else {
        		   System.out.println("Invalid input");
        	   }
           }
           
           // print table's contents, field by field
           while (result.next())
           {
                   System.out.println("Movie Id = " + result.getInt(1));
                   System.out.println("Title = " + result.getString(2));
                   System.out.println("Year = " + result.getInt(3));
                   System.out.println("Director = " + result.getString(4));
                   System.out.println("Banner_url = " + result.getString(5));
                   System.out.println("Trailer_url = " + result.getString(5));
                   System.out.println();
           }
       }
	
	// insert_new_star
	public static void insert_new_star( Connection connection ) throws SQLException
	{
		String first, last, dob, photo_url;
		Scanner scan = new Scanner( System.in );
		// Get first and last names
		System.out.print("Enter star's first name: ");
		first = scan.nextLine();
		System.out.print("Enter star's last name: ");
		last = scan.nextLine();
		
		// If no name is entered leave function
		if( last.trim().isEmpty() && first.trim().isEmpty() )
		{
			System.out.println("ERROR: No name was entered.");
			return;
		}
		
		// If only first name is entered, change last name to first name and set first name to empty string
		if( last.trim().isEmpty() )
		{
			last = first;
			first = "";
		}
		
		// Get dob
		System.out.print("Enter star's date of birth: ");
		dob = scan.nextLine();
		if( dob.trim().isEmpty() )
			dob = "NULL";
		else
			dob = "'" + dob + "'";
		
		// Get photo url
		System.out.print("Enter star's photo url: ");
		photo_url = scan.nextLine();
		if( photo_url.trim().isEmpty() )
			photo_url = "NULL";
		else
			photo_url = "'" + photo_url + "'";
		
		
		// Insert star into database
		Statement insert = connection.createStatement();
        	int retID = insert.executeUpdate("insert into stars values(NULL, '" + first + "', '" + last
        		+ "', " + dob + ", " + photo_url + ")");
        	System.out.println("Number of stars added = " + retID);
	}
	
	// insert_new_customer
	public static void insert_new_customer( Connection connection ) throws SQLException
	{
		String first, last, cc_id, address, email, password;
		Scanner scan = new Scanner( System.in );

		// Get first and last names
		System.out.print("Enter customer's first name: ");
		first = scan.nextLine();
		System.out.print("Enter customer's last name: ");
		last = scan.nextLine();
			
		// If no name is entered leave function
		if( last.trim().isEmpty() && first.trim().isEmpty() )
		{
			System.out.println("ERROR: No name was entered.");
			return;
		}
		// If only first name is entered, change last name to first name and set first name to empty string
		if( last.trim().isEmpty() )
		{
			last = "'" + first + "'";
			first = "''";
		}
		else
		{
			first = "'" + first + "'";
			last = "'" + last + "'";
		}
		
		// Get cc_id
		System.out.print("Enter customer's credit card number: ");
		cc_id  = scan.nextLine();
		if( cc_id.trim().isEmpty() )
		{
			System.out.println("ERROR: No Credit card was entered.");
			return;
		}
		else
			cc_id = "'" + cc_id + "'";
		
		// Get address
		System.out.print("Enter customer's address: ");
		address  = scan.nextLine();
		if( cc_id.trim().isEmpty() )
		{
			System.out.println("ERROR: No address was entered.");
			return;
		}
		else
			address = "'" + address + "'";
		
		// Get email
		System.out.print("Enter customer's email: ");
		email  = scan.nextLine();
		if( email.trim().isEmpty() )
		{
			System.out.println("ERROR: No email was entered..");
			return;
		}
		else
			email = "'" + email + "'";
		
		// Get password
		System.out.print("Enter customer's password: ");
		password  = scan.nextLine();
		if( cc_id.trim().isEmpty() )
		{
			System.out.println("ERROR: No password was entered..");
			return;
		}
		else
			password = "'" + password + "'";
		
		
		// Check if cc_id is in the database. If found add customer to database, else do not add customer
		Statement select = connection.createStatement();
		ResultSet result = select.executeQuery("Select id from creditcards where id = " + cc_id);
		if( result.next() )
		{
			Statement insert = connection.createStatement();
			int retID = insert.executeUpdate("insert into customers values(NULL, " + first + "," + last + "," +
			cc_id + "," + address + "," + email + "," + password + ")");
			System.out.println("Number of customers added = " + retID);
		}
		else
			System.out.println("ERROR: Credit card is not in the database.");

	}
	
	// delete_customer
	public static void delete_customer( Connection connection ) throws SQLException
	{
		Scanner scan = new Scanner( System.in );
		System.out.print("Enter customer id: ");
		String id = scan.nextLine();
       		Statement update = connection.createStatement();
        	int retID = update.executeUpdate("delete from customers where id = " + id);
        	System.out.println("Number of customers deleted = " + retID);
	}
	
	// run_select
	public static void run_select( Connection connection,  String command ) throws SQLException
	{
		Statement select = connection.createStatement();
        	ResultSet result = select.executeQuery( command );
        	ResultSetMetaData metadata = result.getMetaData();
        	while( result.next() )
        	{
        		for( int i = 1; i <= metadata.getColumnCount(); i++ )
        		System.out.println(metadata.getColumnName(i) + ": " + result.getString(i));
        		System.out.println();
        	}
	}
	
	// run_sql_command
	public static void run_sql_command( Connection connection ) throws SQLException
	{
		Scanner scan = new Scanner( System.in );
		// Get SQL command from user
		System.out.print("Enter SQL command: ");
		String command = scan.nextLine();
		
		String temp = command.trim();
		temp = temp.toLowerCase();

		if( temp.startsWith("select") )
			run_select(connection, command);
		else
		{
			Statement statement = connection.createStatement();
			int retID = statement.executeUpdate(command);
			System.out.println("Number of records modified = " + retID);
		}
	}
	
	// print_metadata
	public static void print_metadata( Connection connection ) throws SQLException
	{
        	Statement select = connection.createStatement();
        	ResultSet result = select.executeQuery("Select table_name "
        		+ "from information_schema.tables where table_schema = 'moviedb'" );
        
        	int j = 1;
        	while (result.next())
        	{
        		select = connection.createStatement();
            		String table = result.getString(1);            
            		ResultSet result2 = select.executeQuery("Select * from " + table);
            
            		System.out.println("Table " + j++ + ": " + table);
            
            		ResultSetMetaData metadata = result2.getMetaData();
            		System.out.println("Attributes:");
            
           		 for (int i = 1; i <= metadata.getColumnCount(); i++)
                    		System.out.println( i + ". " + metadata.getColumnName(i) +
                    			" Type: " + metadata.getColumnTypeName(i));
            		System.out.println();
        	}
	}
}
