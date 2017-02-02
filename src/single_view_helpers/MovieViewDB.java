package single_view_helpers;

import util.DatabaseHelper;
import util.Movie;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

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
}
