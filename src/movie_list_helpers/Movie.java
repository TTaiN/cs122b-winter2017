package movie_list_helpers;

import java.util.HashMap;
import java.util.List;

public class Movie {
	// Movie attributes
	private int id;
	private String title;
	private int year;
	private String director;
	private String banner;
	private String trailer;
	private HashMap <Integer, String> stars;  // Star ID and name
	private List<String> genres;

	// Getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getTrailer() {
		return trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	public HashMap<Integer, String> getStars() {
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
}
