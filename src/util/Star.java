package util;

import java.util.LinkedHashMap;

public class Star 
{
	private int id;
	private String first_name;
	private String last_name;
	private String dob;
	private String photo;
	private LinkedHashMap<Integer, String> movies; // list of movies the star is in
	
	public Star() { }
	
	public Star(int id, String first_name, String last_name, String dob, String photo)
	{
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.dob = dob;
		this.photo = photo;
	}
	
	public int getId() 
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getFirstName() 
	{
		return first_name;
	}
	public void setFirstName(String first_name) 
	{
		this.first_name = first_name;
	}
	public String getLastName() 
	{
		return last_name;
	}
	
	public void setLastName(String last_name) 
	{
		this.last_name = last_name;
	}
	
	public String getDOB() 
	{
		return dob;
	}
	
	public void setDOB(String dob) 
	{
		this.dob = dob;
	}
	
	public String getPhoto() 
	{
		if (photo == null || photo.equals(""))
		{
			return "./images/no-image.jpg";
		}
		else return photo;
	}
	
	public void setPhoto(String photo_url) 
	{
		this.photo = photo_url;
	}

	public LinkedHashMap<Integer, String> getMovies() 
	{
		return movies;
	}

	public void setMovies(LinkedHashMap<Integer, String> movieList) 
	{
		this.movies = movieList;
	}

	/* Begin Helpful Functions */
	public String getMoviesHTMLString()
	{
		StringBuilder result = new StringBuilder("");
		if (movies != null && !movies.isEmpty())
		{ 
			for (Integer movie_id : movies.keySet())
			{
				result.append("<a href='./movie.jsp?id=" + movie_id + "'>" + movies.get(movie_id) + "</a><br>");
			}
			//result.deleteCharAt(result.length()-1); // might need to be length-1
			return result.toString();
		}
		else return "(none)";
	}
}
