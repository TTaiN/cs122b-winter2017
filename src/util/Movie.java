package util;

public class Movie
{
	int id;
	String title;
	int year;
	String director;
	String banner_url;
	String trailer_url;
	
	public Movie(int id, String title, int year, String director, String banner_url, String trailer_url)
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.banner_url = banner_url;
		this.trailer_url = trailer_url;
	}
	
	@Override
	public boolean equals(Object other) 
	{
		System.out.println("Called.");
	    if (!(other instanceof Movie)) 
	    {
	        return false;
	    }

	    Movie comparer = (Movie) other;
	    return this.id == comparer.id;
	}
	
	// Begin Getters
	public int getId()
	{
		return this.id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public String getBannerUrl()
	{
		return banner_url;
	}
	
	public String getTrailerUrl()
	{
		return trailer_url;
	}
}











