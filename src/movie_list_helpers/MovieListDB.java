package movie_list_helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import general_helpers.DatabaseHelper;
import general_helpers.Movie;

public class MovieListDB {
	
	public DatabaseHelper dbh; 
	
	public MovieListDB() throws SQLException
	{
		dbh = new DatabaseHelper();
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
		ResultSet rs2 = dbh.executePreparedStatement("select id, first_name, last_name from stars where id =some "
				+ "(select star_id from stars_in_movies where movie_id= " + m.getId() + ")");
		while(rs2.next())
		{
			stars.put(rs2.getInt(1), rs2.getString(2) + " " + rs2.getString(3));
		}
		m.setStars(stars);
		
		// Get genres of the movie
		LinkedHashMap <Integer, String> genres = new LinkedHashMap<Integer, String>();
		ResultSet rs3 = dbh.executePreparedStatement("select id, name from genres where id =some "
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
			String where = Where.getWhere(genre, "name");
			
			ResultSet rs = dbh.executePreparedStatement("select * from movies where id =some"
					+ " (select movie_id from genres_in_movies where genre_id=some"
					+ " (select id from genres where " + where + ")) "
					+ " order by " + sort + " limit " + limit + " offset " + offset);
			while(rs.next())
			{
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
			ResultSet rs = dbh.executePreparedStatement("select * from movies where left(title, 1) = '" + 
					firstChar + "' order by " + sort + " limit " + limit + " offset " + offset);
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
			String starWhere = Where.getWhere(star, "s.name");
			String titleWhere = Where.getWhere(title, "title");
			String directorWhere = Where.getWhere(director, "director");
			String yearWhere = Where.getWhere(year, "year");

			ResultSet rs = dbh.executePreparedStatement("select * from movies where " +
					titleWhere + " and " + directorWhere + " and " + yearWhere + " and id=some "
					+ "(select movie_id from stars_in_movies where star_id=some"
					+ "(select s.id from (select id, concat(first_name, ' ', last_name) as name from "
					+ "stars) as s where " + starWhere + ")) order by " + sort + " limit " + limit
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
