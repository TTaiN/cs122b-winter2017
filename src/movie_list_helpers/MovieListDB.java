package movie_list_helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import util.Movie;

public class MovieListDB {
	
    private static String loginUser = "cs122b";
    private static String loginPasswd = "cs122bgroup42";
    private static String loginUrl = "jdbc:mysql://35.167.240.46/moviedb";
	
	// Connect to moviedb
	public static Connection getConnection(){  
		Connection connection=null;  
        try{  
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        }catch(Exception e){System.out.println(e);}  
        return connection;  
    }
	
	// getMovie
	// Get one movie from current row
	public static Movie getMovie(ResultSet rs, Connection connection) throws SQLException
	{
		Movie m = new Movie();

		// Get movie attributes
		m.setId(rs.getInt(1));
		m.setTitle(rs.getString(2));
		m.setYear(rs.getInt(3));
		m.setDirector(rs.getString(4));
		m.setBanner(rs.getString(5));
		m.setTrailer(rs.getString(6));
		
		// Get stars of the movie
		LinkedHashMap <Integer, String> stars = new LinkedHashMap<Integer, String>(); // note to Mo: here we should use LinkedHashMap to preserve any ordering
		PreparedStatement query = connection.prepareStatement(
				"select id, first_name, last_name from stars where id =some "
				+ "(select star_id from stars_in_movies where movie_id= " + m.getId() + ")");
		ResultSet rs2 = query.executeQuery();
		while(rs2.next())
		{
			stars.put(rs2.getInt(1), rs2.getString(2) + " " + rs2.getString(3));
		}
		m.setStars(stars);
		
		// Get genres of the movie
		List <String> genres = new ArrayList<String>();
		PreparedStatement query2 = connection.prepareStatement("select name from genres where id =some "
				+ "(select genre_id from genres_in_movies where movie_id= " + m.getId() + ")");
		ResultSet rs3 = query2.executeQuery();
		while(rs3.next())
		{
			genres.add(rs3.getString(1));
		}
		m.setGenres(genres);

		return m;
	}
	
	// GetMovies methods
	// Get a list of all Movies matching the search criteria
	
	// getMovies (Genre)
	public static List<Movie> getMovies(int limit, int offset, String genre, String sort)
	{
		List<Movie> movieList = new ArrayList<Movie>();
		try
		{
			String where = Where.getWhere(genre, "name");
			
			Connection connection = getConnection();
			PreparedStatement query = 
					connection.prepareStatement("select * from movies where id =some"
							+ " (select movie_id from genres_in_movies where genre_id=some"
							+ " (select id from genres where " + where + ")) "
							+ " order by " + sort + " limit " + limit + " offset " + offset);
			ResultSet rs = query.executeQuery();
			while(rs.next())
			{
				movieList.add(getMovie(rs, connection));
			}
		}
		catch(Exception e){System.out.println(e);}
		return movieList;
	}
	
	// getMovies (First character)
	public static List<Movie> getMovies(int limit, int offset, char firstChar, String sort)
	{
		List<Movie> movieList = new ArrayList<Movie>();
		try
		{
			Connection connection = getConnection();
			PreparedStatement query = 
					connection.prepareStatement("select * from movies where left(title, 1) = '" + 
							firstChar + "' order by " + sort + " limit " + limit + " offset " + offset);
			ResultSet rs = query.executeQuery();
			while(rs.next())
			{
				movieList.add(getMovie(rs, connection));
			}
		}
		catch(Exception e){System.out.println(e);}
		return movieList;
	}
	
	// getMovies (Search)
	public static List<Movie> getMovies(int limit, int offset, String title, String director,
			String year, String star, String sort)
	{
		List<Movie> movieList = new ArrayList<Movie>();
		try
		{
			String starWhere = Where.getWhere(star, "s.name");
			String titleWhere = Where.getWhere(title, "title");
			String directorWhere = Where.getWhere(director, "director");
			String yearWhere = Where.getWhere(year, "year");
			Connection connection = getConnection();
			PreparedStatement query = connection.prepareStatement("select * from movies where " +
					titleWhere + " and " + directorWhere + " and " + yearWhere + " and id=some "
					+ "(select movie_id from stras_in_movies where star_id=some"
					+ "(select s.id from (select id, concat(first_name, ' ', last_name) as name from "
					+ "stars) as s where " + starWhere + ")) order by " + sort + " limit " + limit
					+ " offset " + offset);
			ResultSet rs = query.executeQuery();
			while(rs.next())
			{
				movieList.add(getMovie(rs, connection));
			}
		}
		catch(Exception e){System.out.println(e);}
		return movieList;
	}

}
