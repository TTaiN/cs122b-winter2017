import java.util.ArrayList;
import java.util.List;

public class Movie
{
	private String id;
	private String title;
	private String year;
	private List<String> directors;
	private List<String> genres;
	
	public Movie() { 
		this.directors = new ArrayList<String>();
		this.genres = new ArrayList<String>();
		this.title = "Null";
		this.year = "Null";
	}
	
	public Movie(String id, String title, String year, List<String> directors, List<String> genres)
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.setDirectors(directors);
		this.setGenres(genres);
	}
	
	// Begin Getters

	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getYear()
	{
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public List<String> getDirectors() {
		return directors;
	}

	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addDirector( String director )
	{
		this.directors.add(director); 
	}
	
	public void addGenre( String genre )
	{
		this.genres.add(genre); 
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Movie Details - ");
		sb.append("Id:" + getId());
		sb.append(", ");
		sb.append("Title:" + getTitle());
		sb.append(", ");
		sb.append("Year:" + getYear());
		sb.append(", ");
		sb.append("Directors:" + getDirectors().toString());
		sb.append(",");
		sb.append("Genres:" + getGenres().toString());
		sb.append(".");
		return sb.toString();
	}
	

}
