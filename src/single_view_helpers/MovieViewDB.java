package single_view_helpers;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

import general_helpers.DatabaseHelper;
import general_helpers.Movie;

public class MovieViewDB 
{
	DatabaseHelper db;
	
	public MovieViewDB() throws SQLException
	{
		db = new DatabaseHelper();
	}
	
	public Movie getMovie(int movie_id) throws SQLException
	{
		ResultSet rs = db.executePreparedStatement("SELECT * FROM movies WHERE id = " + movie_id);
		if (rs.next())
		{
			return new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"), rs.getString("banner_url"), rs.getString("trailer_url"), 0);
		}
		else throw new SQLException();
	}
	
	public Movie getCompleteMovie(int movie_id) throws SQLException
	{
		Movie movie = getMovie(movie_id);		
		movie.setStars(getStarsForMovie(movie_id));
		movie.setGenres(getGenresForMovie(movie_id));
		return movie;
		
	}
	
	public LinkedHashMap<Integer, String> getGenresForMovie(int movie_id) throws SQLException
	{
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		
		ResultSet genre_list = db.executePreparedStatement("SELECT * FROM genres WHERE genre.id IN "
		+ "(SELECT genres_in_movies.genre_id FROM genre_in_movies WHERE movie_id = "+ movie_id + ");");
		
		while (genre_list.next())
		{
			result.put(genre_list.getInt("id"), genre_list.getString("name"));
		}
		return result;
	}
	
	public LinkedHashMap<Integer, String> getStarsForMovie(int movie_id) throws SQLException
	{
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		
		ResultSet star_list = db.executePreparedStatement("SELECT * FROM stars WHERE stars.id IN "
		+ "(SELECT stars_in_movies.star_id FROM stars_in_movies WHERE movie_id = "+ movie_id + ");");
		
		while (star_list.next())
		{
			result.put(star_list.getInt("id"), star_list.getString("first_name") + " " + star_list.getString("last_name"));
		}
		return result;
	}
	
	public void close()
	{
		try
		{
			db.closeConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
