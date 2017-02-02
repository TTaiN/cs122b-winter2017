package single_view_helpers;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

import general_helpers.DatabaseHelper;
import general_helpers.Movie;
import general_helpers.Star;

public class StarViewDB 
{
	DatabaseHelper db;
	
	public StarViewDB() throws SQLException
	{
		this.db = new DatabaseHelper();
	}
	
	public Star getStar(int star_id) throws SQLException
	{
		ResultSet rs = db.executePreparedStatement("SELECT * FROM stars WHERE id = " + star_id);
		if (rs.next())
		{
			return new Star(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("dob"), rs.getString("photo_url"));
		}
		else throw new SQLException();
	}
	
	public LinkedHashMap<Integer, String> getMoviesForStar(int star_id) throws SQLException
	{
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		
		ResultSet movie_list = db.executePreparedStatement("SELECT * FROM movies WHERE movies.id IN "
		+ "(SELECT stars_in_movies.movie_id FROM stars_in_movies WHERE star_id = "+ star_id + ");");
		
		while (movie_list.next())
		{
			result.put(movie_list.getInt("id"), movie_list.getString("title"));
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
