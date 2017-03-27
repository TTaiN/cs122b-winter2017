import java.util.LinkedHashMap;

public class Star 
{
	private int id;
	private String first_name = "NULL";
	private String last_name = "NULL";
	private String stage_name = "NULL";
	private String dob = "NULL";
	private String photo = "NULL";
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
	
	public String getFullName()
	{
		return first_name + " " + last_name;
	}
	
	public String getFirstName() 
	{
		return first_name;
	}
	
	public void setFirstName(String first_name) 
	{
		this.first_name = first_name.trim();
	}
	
	public String getStageName() 
	{
		return stage_name;
	}
	
	public void setStageName(String stage_name) 
	{
		this.stage_name = stage_name;
	}
	
	public String getLastName() 
	{
		return last_name;
	}
	
	public void setLastName(String last_name) 
	{
		this.last_name = last_name.trim();
	}
	
	public String getDOB() 
	{
		return (dob.equals("") ? "NULL" : this.dob);
	}
	
	public boolean hasFullName()
	{
		return (!this.first_name.equals("NULL") && !this.last_name.equals("NULL"));
	}
	
	public void setDOB(String dob) 
	{
		this.dob = dob;
	}
	
	public String getPhoto() 
	{
		return photo;
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
}
