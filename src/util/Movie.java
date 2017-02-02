package util;

import java.util.HashMap;
import java.util.List;

// Note to Mo: We both had movie classes. To avoid duplicate I just merged it here.

public class Movie
{
	private int id;
	private String title;
	private int year;
	private String director;
	private String banner;
	private String trailer;
	private HashMap <Integer, String> stars;
	private List<String> genres;

	public Movie() { }
	
	public Movie(int id, String title, int year, String director, String banner, String trailer)
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.banner = banner;
		this.trailer = trailer;
		this.stars = new HashMap<Integer, String>();
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
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public String getBanner()
	{
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	public String getTrailer()
	{
		return trailer;
	}
	
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	
	public void addStar(int id, String name)
	{
		stars.put(id, name);
	}
	
	public HashMap<Integer, String> getStars()
	{
		return stars;
	}
	
	public void setStars(HashMap<Integer, String> stars) {
		this.stars = stars;
	}
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	
	/* Begin Useful Functions */
	
	
}











