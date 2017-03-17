package movie_list_helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import general_helpers.DatabaseHelper;
import general_helpers.Movie;

public class MovieListDB {
	
	public DatabaseHelper dbh; 
	public Connection conn;
	
	public MovieListDB() throws SQLException
	{
		dbh = new DatabaseHelper(false);
		conn = dbh.getConnection();
	}
	
	private static String prepareSearchWord(ArrayList<String> keywords)
	{
		String query = "";
		int sz = keywords.size();
		
		if(sz == 0)
			return "";
		
		for(int i = 0; i < sz; i++){
			if(i < sz-1)
				query += "+" + keywords.get(i) + " ";
			else
				query += "+" + keywords.get(i) + "*";
		}
		return query;
	}
	
	public List<Movie> getMobileMovies(String title) throws SQLException // By Title
	{
		List<Movie> movieList = new ArrayList<Movie>();		

		PreparedStatement statement = conn.prepareStatement("SELECT title FROM movies WHERE MATCH(title) AGAINST (? in boolean mode);");
		ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(title.split(" ")));
		statement.setString(1,  prepareSearchWord(keywords));
		ResultSet rs = statement.executeQuery();
		
		while(rs.next())
		{
			movieList.add(getMobileMovie(rs));
		}
		
		return movieList;
	}
	
	public Movie getMobileMovie(ResultSet rs) throws SQLException
	{
		Movie m = new Movie();
		m.setTitle(rs.getString("title"));
		
		return m;
	}
	
	// getMovie
	// Get one movie from current row
	public Movie getMovie(ResultSet rs) throws SQLException
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
		LinkedHashMap <Integer, String> stars = new LinkedHashMap<Integer, String>();
		
		// Prepared Statement
		/*
		PreparedStatement statement = conn.prepareStatement("select id, first_name, last_name from stars where id in "
				+ "(select star_id from stars_in_movies where movie_id= ?)");
		statement.setInt(1, m.getId());
		ResultSet rs2 = statement.executeQuery();
		*/
		
		// SQL Statement
		ResultSet rs2 = dbh.executeSQL("select id, first_name, last_name from stars where id in "
				+ "(select star_id from stars_in_movies where movie_id= " + m.getId() + ")");
		
		while(rs2.next())
		{
			stars.put(rs2.getInt(1), rs2.getString(2) + " " + rs2.getString(3));
		}
		m.setStars(stars);
		
		// Get genres of the movie
		LinkedHashMap <Integer, String> genres = new LinkedHashMap<Integer, String>();
		
		// Prepared Statement
		/*
		statement = conn.prepareStatement("select id, name from genres where id in "
				+ "(select genre_id from genres_in_movies where movie_id= ?)");
		statement.setInt(1, m.getId());
		ResultSet rs3 = statement.executeQuery();
		*/
		
		// SQL Statement
		ResultSet rs3 = dbh.executeSQL("select id, name from genres where id in "
				+ "(select genre_id from genres_in_movies where movie_id= " + m.getId() + ")");
		
		while(rs3.next())
		{
			genres.put(rs3.getInt(1), rs3.getString(2));
		}
		m.setGenres(genres);

		return m;
	}
	
	// GetMovies methods
	// Get a list of all Movies matching the search criteria
	
	// getMovies (Genre)
	public List<Movie> getMovies(int limit, int offset, String genre, String sort)
	{
		List<Movie> movieList = new ArrayList<Movie>();
		try
		{
			String where = Where.addPercent(genre);
			// Prepared Statement
			/*
			PreparedStatement statement = conn.prepareStatement("select * from movies where id in (select movie_id from genres_in_movies where genre_id in (select id from genres where name like ?)) order by ? limit ? offset ?;");
			statement.setString(1, where);
			statement.setString(2, sort);
			statement.setInt(3, limit+1);
			statement.setInt(4, offset);
			ResultSet rs = statement.executeQuery();
			*/
			
			// SQL Statement
			ResultSet rs = dbh.executeSQL("select * from movies where id in"
					+ " (select movie_id from genres_in_movies where genre_id in"
					+ " (select id from genres where name like '" + where + "' )) "
					+ " order by " + sort + " limit " + (limit+1) + " offset " + offset);
			while(rs.next())
			{
				System.out.println(getMovie(rs).toString());
				movieList.add(getMovie(rs));
			}
		}
		catch(Exception e){System.out.println(e);}
		return movieList;
	}
	
	// getMovies (First character)
	public List<Movie> getMovies(int limit, int offset, char firstChar, String sort)
	{
		List<Movie> movieList = new ArrayList<Movie>();
		try
		{
			// Prepared Statement
			/*
			PreparedStatement statement = conn.prepareStatement("select * from movies where left(title, 1) = ? order by ? limit ? offset ?;");
			statement.setString(1, Character.toString(firstChar));
			statement.setString(2, sort);
			statement.setInt(3, limit+1);
			statement.setInt(4, offset);
			System.out.println(statement);
			ResultSet rs = statement.executeQuery();
			*/
			
			// SQL Statement
			ResultSet rs = dbh.executeSQL("select * from movies where left(title, 1) = '" + 
					firstChar + "' order by " + sort + " limit " + (limit+1) + " offset " + offset);
			
			while(rs.next())
			{
				movieList.add(getMovie(rs));
			}
		}
		catch(Exception e){System.out.println(e);}
		return movieList;
	}
	
	// getMovies (Search)
	public List<Movie> getMovies(int limit, int offset, String title, String director,
			String year, String star, String sort)
	{
		List<Movie> movieList = new ArrayList<Movie>();
		try
		{
			String starWhere = Where.addPercent(star);
			String titleWhere = Where.addPercent(title);
			String directorWhere = Where.addPercent(director);
			String yearWhere = Where.addPercent(year);
			
//			String starWhere = Where.getWhereEdth(star, "s.name");
//			String titleWhere = Where.getWhereEdth(title, "title");
//			String directorWhere = Where.getWhereEdth(director, "director");
//			String yearWhere = Where.getWhereEdth(year, "year");
			
			// Prepared Statement
			/*
			PreparedStatement statement = conn.prepareStatement("select * from movies where "
						+ "title like ? and director like ? and year like ? and id in "
						+ "(select movie_id from stars_in_movies where star_id in"
						+ "(select s.id from (select id, concat(first_name, ' ', last_name) as name from "
						+ "stars) as s where s.name like ?)) order by ? limit ?"
						+ " offset ?");
			statement.setString(1, titleWhere);
			statement.setString(2, directorWhere);
			statement.setString(3, yearWhere);
			statement.setString(4, starWhere);
			statement.setString(5, sort);
			statement.setInt(6, limit+1);
			statement.setInt(7, offset);

			System.out.println(statement);
			ResultSet rs = statement.executeQuery();
			*/
			
			// SQL Statement
			ResultSet rs = dbh.executeSQL("select * from movies where "
					+ "title like '" + titleWhere + "' and director like '" + directorWhere 
					+ "' and year like '" + yearWhere + "' and id in "
					+ "(select movie_id from stars_in_movies where star_id in"
					+ "(select s.id from (select id, concat(first_name, ' ', last_name) as name from "
					+ "stars) as s where s.name like '" + starWhere + "')) order by " + sort + " limit " + (limit+1)
					+ " offset " + offset);
			while(rs.next())
			{
				movieList.add(getMovie(rs));
			}
		}
		catch(Exception e){System.out.println(e);}
		return movieList;
	}

}
