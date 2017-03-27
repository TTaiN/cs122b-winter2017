import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args)
	{
	    ActorsParser actorsParser = new ActorsParser();
		CastsParser castsParser = new CastsParser();
		MainParser mainParser = new MainParser();

	    System.out.println("-- CS 122B Group 42 (Project 3, Task 5) ---");

	    try 
	    {
			DatabaseHelper db = new DatabaseHelper();
			db.setAutoCommit(false);
			
			ResultSet rs = db.executeSQL("Select title from movies");
			HashSet<String> DBMovies = new HashSet<String>();
			while(rs.next())
			{
				DBMovies.add(rs.getString("title").toLowerCase().trim());
			}
			
			HashSet<String> DBStars = new HashSet<String>();
			rs = db.executeSQL("Select first_name, last_name from stars");
			while(rs.next())
			{
				DBStars.add(rs.getString("first_name").toLowerCase().trim() + " " + rs.getString("last_name").toLowerCase().trim());
			}
			
			HashSet<String> DBGenres = new HashSet<String>();
			rs = db.executeSQL("Select name from genres");
			while(rs.next())
			{
				DBGenres.add(rs.getString("name").toLowerCase().trim());
			}
			
			mainParser.runParser(); // Number of Movies: 12115
			HashMap<String, Star> xmlStars = actorsParser.runParser(); // Number of Valid Stars: 6863
			castsParser.runParser(); // Number of Movies in Cast: 8996. // Number of Stars in Cast: 47245.
			
			System.out.println("[INFO] Beginning Movie database...");
			HashMap<String, String> xmlMovies = mainParser.pushToDB(db, DBMovies, DBGenres);
			System.out.println("[INFO] Beginning Actors/Stars database...");
			HashMap<String, Integer> newDBStars = actorsParser.pushToDB(db, DBStars); // Returns HashMap<StarFirstName+""+StarLastName, Star_ID_In_Database>
			
			HashMap<String, Integer> dbMovieIds = new HashMap<String, Integer>();
			rs = db.executeSQL("select title, id from movies");
			while(rs.next())
			{
				dbMovieIds.put(rs.getString("title").toLowerCase().trim(), rs.getInt("id"));
			}
			HashSet<Integer> existingIds = new HashSet<Integer>();
			rs = db.executeSQL("select movie_id from stars_in_movies");
			while(rs.next())
			{
				existingIds.add(rs.getInt("movie_id"));
			}
			

			System.out.println("[INFO] Beginning Casts database... this may take a while.");
			castsParser.pushToDB(db, xmlStars, xmlMovies, newDBStars, dbMovieIds, existingIds); // Only pushes (Total # = 23200) out of 47245 entries, because it ignores all Stage Names w/ no First/Last name
			System.out.println("[INFO] All XMLs and their data parsed and added to the database.");
			db.setAutoCommit(true);
			db.closeConnection();
			System.out.println("[INFO] Terminated.");

		} 
	    catch (SQLException e) 
	    {
			e.printStackTrace();
		}


	}
}
