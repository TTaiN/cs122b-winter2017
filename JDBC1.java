// JDBC Example - printing a database's metadata
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson


import java.sql.*;                              // Enable SQL processing
import java.util.Scanner;

public class JDBC1
{
	
       public static void main(String[] arg) throws Exception
       {

               // Incorporate mySQL driver
               Class.forName("com.mysql.jdbc.Driver").newInstance();

                // Connect to the test database
               Connection connection = DriverManager.getConnection("jdbc:mysql://ttain.tk/moviedb?autoReconnect=true&useSSL=false","pupplayer", "pupplayer");
              
               Scanner s = new Scanner(System.in);
               while(true) {
        		   printMenu();
        		   String input = s.nextLine();
        		   if (input.equals("1")) {
        			   printMovie(connection);
        		   } else if (input.equals("2")) {
        			   
        		   } else if (input.equals("3")) {
        			   
        		   } else if (input.equals("4")) {
        			   
        		   } else if (input.equals("5")) {
        			   
        		   } else if (input.equals("6")) {
        			   
        		   } else if (input.equals("7")) {
        			   break;
        		   } else {
        			   System.out.println("Invalid command");
        		   }      	   
               }
               s.close();
               
               
       }
       
       public static void printMenu() {
    	   System.out.println("Select a menu:");
    	   System.out.println("1: Print movie featuring a given star");
    	   System.out.println("2: Insert new star");
    	   System.out.println("3: Insert new customer");
    	   System.out.println("4: Delete customer");
    	   System.out.println("5: Provide metadata");
    	   System.out.println("6: Enter SQL command");
    	   System.out.println("7: Exit menu");
       }
       
       public static  void printMovie(Connection connection) throws SQLException{
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
       
}
