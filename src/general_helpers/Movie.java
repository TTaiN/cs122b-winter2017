package general_helpers;

import java.util.LinkedHashMap;
import java.lang.StringBuilder;

public class Movie
{
	private int id;
	private String title;
	private int year;
	private String director;
	private String banner;
	private String trailer;
	private LinkedHashMap <Integer, String> stars;
	private LinkedHashMap<Integer, String> genres;
	private double price = 14.99;
	private int quantity = 0;
	
	public Movie() { }
	
	public Movie(int id, String title, int year, String director, String banner, String trailer)
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.banner = banner;
		this.trailer = trailer;
	}
	
	public Movie(int id, String title, int year, String director, String banner, String trailer, int quantity)
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.banner = banner;
		this.trailer = trailer;
		this.quantity = quantity;
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
		if (banner == null || banner.equals("") || banner.equals("n/a"))
		{
			return "./images/no-image.jpg";
		}
		else return banner;
	}
	
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	public String getTrailer()
	{
		if (trailer == null || trailer.equals(""))
		{
			return "(none)";
		}
		else return trailer;
	}
	
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	
	public void addStar(int id, String name)
	{
		stars.put(id, name);
	}
	
	public LinkedHashMap<Integer, String> getStars()
	{
		return stars;
	}
	
	public void setStars(LinkedHashMap<Integer, String> stars) 
	{
		this.stars = stars;
	}
	public LinkedHashMap<Integer, String> getGenres() 
	{
		return genres;
	}
	
	public void setGenres(LinkedHashMap<Integer, String> genres) {
		
		this.genres = genres;
	}
	
	public double getSubtotal()
	{
		return price * quantity;
	}
	
	public double getPrice() 
	{
		return price;
	}

	public void setPrice(double price) 
	{
		this.price = price;
	}

	public int getQuantity() 
	{
		return quantity;
	}

	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}
	
	/* Begin Useful Functions */
	public String getStarsHTMLString()
	{
		StringBuilder result = new StringBuilder("");
		if (stars != null && !stars.isEmpty())
		{ 
			for (Integer star_id : stars.keySet())
			{
				result.append("<a href='./star?id=" + star_id + "'>" + stars.get(star_id) + "</a><br>");
			}
			return result.toString();
		}
		else return "(none)";
	}
	
	public String getGenresHTMLString()
	{
		StringBuilder result = new StringBuilder("");
		if (genres != null && !genres.isEmpty())
		{ 
			for (Integer genre_id : genres.keySet())
			{
				result.append("<a href='./movielist?genre=" + genres.get(genre_id) + "'>" + genres.get(genre_id) + "</a><br>");
			}
			return result.toString();
		}
		else return "(none)";
	}
}











