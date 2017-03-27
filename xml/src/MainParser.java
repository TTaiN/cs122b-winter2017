import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MainParser extends DefaultHandler{

	List<Movie> movies;
	HashMap<String, String> xmlMovies= new HashMap<String, String>();
	HashSet<String> genres = new HashSet<String>();
	HashSet<String> moviesXml = new HashSet<String>();
	private String tempVal;
	private boolean error = false;
	private String errMsg = "";

	
	//to maintain context
	private Movie tempMovie;
	
	public MainParser(){
		movies = new ArrayList<Movie>();
	}
	
	public void runParser() {
		parseDocument();
	}
	
	// pushGenres
	public void pushGenres(DatabaseHelper db, HashSet<String> DBGenres)
	{
		try
		{
			String insertGenre = "insert into genres (name) values(?)";
			PreparedStatement statement = db.prepareStatement(insertGenre);
			Iterator<String> it = genres.iterator();
			
			// Add genres
			while(it.hasNext())
			{
				String genre = (String) it.next();
				// Check if genre already exists
				if(!DBGenres.contains(genre.toLowerCase()))
				{
					if(genre == "" || genre == null)
						continue;
					else
					{
						statement.setString(1, genre);
						statement.addBatch();
					}
				}	
			}
			
			// Execute and commit changes
			statement.executeBatch();
			db.commit();
			System.out.println("[INFO] New Genres Added");
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	// pushMoviesAndGenresInMovies
	public void pushMoviesAndGenresInMovies(DatabaseHelper db, HashSet<String> DBMovies, HashMap<String, Integer> dbGenreIds)
	{
		try
		{
			// Set initial movieId to 12000
			int movieId = 12000;
			// Declare to prepared statements (one for adding records to movies and one for adding records 
			// to genres_in_movies)
			String insertGenreInMovie = "insert into genres_in_movies (genre_id, movie_id) values(?, ?)";
			String insertMovie = "insert into movies (id, title, year, director) values(?, ?, ?, ?)";
			PreparedStatement statement = db.prepareStatement(insertMovie);
			PreparedStatement statement2 = db.prepareStatement(insertGenreInMovie);
			int count = 0;
			Iterator<Movie> it2 = movies.iterator();
			while(it2.hasNext())
			{
				Movie m = (Movie) it2.next();
				// Check if movie already exists in moviedb
				if(!DBMovies.contains(m.getTitle().toLowerCase()))
				{
					if(m.getYear() == "Null")
						continue;
					else
					{
						// Get list of genreIds from genres
						List<Integer> genreIds = new ArrayList<Integer>();
						for(int i = 0; i < m.getGenres().size(); i++)
						{
							String genreName = m.getGenres().get(i);
							Integer id = dbGenreIds.get(genreName);
							genreIds.add(id);
						}
						// Set attributes for new movie record
						statement.setInt(1, movieId);
						statement.setString(2, m.getTitle());
						statement.setString(3, m.getYear());
						String directors = m.getDirectors().toString();
						directors = directors.substring(1, directors.length() -1);
						statement.setString(4, directors.trim());
						statement.addBatch();
						
						// Set attributes for new genres_in_movies records
						for(int i = 0; i < genreIds.size()-1; i++)
						{
							statement2.setInt(1, genreIds.get(i));
							statement2.setInt(2, movieId);
							statement2.addBatch();
						}
						// Execute batches
						if(++count == 1000)
						{
							statement.executeBatch();
							statement2.executeBatch();
							count = 0;
						}
						xmlMovies.put(m.getId(), m.getTitle());
						movieId++;
					}
				}	
			}
			// Execute and commit both statements
			statement.executeBatch();
			statement2.executeBatch();
			db.commit();
			System.out.println("DONE");
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public HashMap<String, String> pushToDB(DatabaseHelper db, HashSet<String> DBMovies, HashSet<String> DBGenres)
	{
		pushGenres(db, DBGenres);
		System.out.println("[INFO] Starting Movie insertion... This may take a while.");
		HashMap<String, Integer> dbGenreIds = new HashMap<String, Integer>();
		try{
			ResultSet rs = db.executeSQL("Select name, id from genres");
			while(rs.next())
			{
				dbGenreIds.put(rs.getString("name"), rs.getInt("id"));
			}
			pushMoviesAndGenresInMovies(db, DBMovies, dbGenreIds);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return xmlMovies;
	}

	private void parseDocument() {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse("mains243.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * Iterate through the list and print
	 * the contents
	 */
	public void printData(){
		
		System.out.println("No of Movies '" + movies.size() + "'.");
		
		Iterator<Movie> it = movies.iterator();
		int count = 0;
		while(it.hasNext()) {
			System.out.println("#" + ++count + " " + it.next().toString());
		}
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("film")) {
			//create a new instance of employee
			tempMovie = new Movie();
		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("film")) {
			if(error == true)
			{
				System.out.println("[NOTICE] Film entry: " + tempMovie.getId() + " titled: " + 
									tempMovie.getTitle() + " was not added due to " + errMsg);
				error = false;
				errMsg = "";
			}
			else
				movies.add(tempMovie);
		}else if (qName.equalsIgnoreCase("fid")) {
			tempMovie.setId(tempVal.trim());
		}else if (qName.equalsIgnoreCase("t")) {
			if(moviesXml.contains(tempVal.trim().replaceAll("[^A-Za-z \\~\\:]", "").trim().toLowerCase()))
			{
				tempMovie.setTitle(tempVal.trim().replaceAll("[^A-Za-z \\~\\:]", "").trim());
				error = true;
				errMsg = "movie duplicate";
			}
			else
			{
				moviesXml.add(tempVal.trim().replaceAll("[^A-Za-z \\~\\:]", "").trim().toLowerCase());
				tempMovie.setTitle(tempVal.trim().replaceAll("[^A-Za-z \\~\\:]", "").trim());
			}
		}else if (qName.equalsIgnoreCase("year")) {
			try
			{
				int year = Integer.parseInt(tempVal.trim());
				if(year > 1800 && year < 2025)
				{
					tempMovie.setYear(tempVal.trim());
				}
				else
				{
					error = true;
					errMsg = "missing year";
					tempMovie.setYear("Null");
				}
			}catch(NumberFormatException e){
				error = true;
				errMsg = "misformatted year";
				tempMovie.setYear("Null");				
			}
		}else if (qName.equalsIgnoreCase("cat")) {
			tempMovie.addGenre(tempVal.replaceAll("[^A-Za-z \\.\\~\\\\\\-]", "").trim().toLowerCase());
			if(!genres.contains(tempVal.replaceAll("[^A-Za-z \\.\\~\\\\\\-]", "").trim().toLowerCase()))
				genres.add(tempVal.replaceAll("[^A-Za-z \\.\\~\\\\\\-]", "").trim().toLowerCase());
		}else if (qName.equalsIgnoreCase("dirn")) {
			String temp = tempMovie.getDirectors().toString();
			temp = temp.substring(1, temp.length() -1) + ", " + tempVal.replaceAll("[^A-Za-z \\.\\~\\']", "").trim();
			if(temp.length() < 100)
			{
				tempMovie.addDirector(tempVal.replaceAll("[^A-Za-z \\.\\~\\']", "").trim());
			}else{
				error = true;
				errMsg = "long list of directors (exceeding 100 characters)";
			}
		}
	}
	

}



