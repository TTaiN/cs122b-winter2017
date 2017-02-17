<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>

<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<%@ page import="general_helpers.DatabaseHelper" %>

<%
	if (session.getAttribute("username") == null || request.getAttribute("movie") == null)
	{
		response.sendRedirect("./login");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./style/main.css"/> 
<title>Dashboard</title>
</head>
<body>

	<%
		TopMenu.print(response.getWriter());
	%>
	    	
<center>
<H1>Dashboard</H1>
<H3>1: Insert new star</H3>
<form ACTION="./dashboardInsertStar" METHOD="GET">
  		<input type="text" name="id" placeholder="Star ID.."><br><br>
  		<input type="text" name="firstName" placeholder="First Name.. (Leave blank if has one name)"><br><br>
  		<input type="text" name="lastName" placeholder="Last Name.."><br><br>
  		<input type="text" name="dob" placeholder="Date of Birth.."><br><br>
  		<input type="text" name="photoUrl" placeholder="Photo URL.."><br><br>
  		<input type="submit" value="Submit"><br>
</form>


<H3>2: Print metadata</H3>

<form action="./_dashboard" method="GET">
	<center><input type="submit" name="metadata" value="Print Metadata"/><br></center>
</form>	

<H3>3: Add movie</H3>
	
</center>


	    	<%
	       /* String input = s.nextLine();
		   if (input.equals("1")) {
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
				 */
				
				// Insert star into database
				/* Statement insert = connection.createStatement();
		     	int retID = insert.executeUpdate("insert into stars values(NULL, '" + first + "', '" + last
		     		+ "', " + dob + ", " + photo_url + ")");
		     	System.out.println("Number of stars added = " + retID); */

/* 	       
	       else if (input.equals("2")) 
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

	       else 
		       System.out.println("Invalid command");  
    }
    catch( SQLException e)
    {
	       System.out.println(e.getMessage());
    }
}
s.close();               


//insert_new_star
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
	
	
	*/
%>

</body>
</html>