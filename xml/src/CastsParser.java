import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.StringBuilder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CastsParser extends DefaultHandler
{
	private HashMap<String, ArrayList<String>> casts; // Key = Movie Identifier, Value = List of Stars
	private String currentMovieID;
	private ArrayList<String> currentList;
	
	private String tempVal;
	
	public CastsParser()
	{
		casts = new HashMap<String, ArrayList<String>>();
	}
	
	public void runParser() // Change to run() later.
	{
		parseDocument();
	}

	private void parseDocument() 
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try 
		{
			SAXParser sp = spf.newSAXParser();
			sp.parse("casts124.xml", this);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void printData()
	{	
		int starCount = 0;
		int counter = 1;

		for(String key : casts.keySet())
		{
			System.out.println("\n[#" + counter++ + "] Movie ID: " + key + "");
			StringBuilder stars = new StringBuilder("  -> ");
			for (String value : casts.get(key))
			{
				stars.append(value);
				stars.append(", ");
				starCount++;
			}
			stars.delete(stars.length()-2, stars.length());
			System.out.println(stars.toString());
		}
		System.out.println("\n[INFO] Numbers of Movies: " + casts.size() + ".");
		System.out.println("[INFO] Numbers of Stars in Movies: " + starCount + ".");
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		tempVal = new String(ch,start,length);
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
		tempVal = "";
		if(qName.equalsIgnoreCase("filmc"))  // New film
		{
			currentList = new ArrayList<String>();
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		if (qName.equalsIgnoreCase("f")) // Movie ID that references movies in main
		{
			currentMovieID = tempVal;
		}
		else if (qName.equalsIgnoreCase("a")) // Stage Name of the star (NOT first/last name!)
		{
			if (!tempVal.equals("") && !tempVal.replaceAll(" ",  "").equals("sa")) // sa = Some Actor.. means no data.
			{
				if (casts.get(currentMovieID) == null)
				{
					casts.put(currentMovieID, currentList);
				}
				currentList.add(tempVal.replaceAll("[^A-Za-z \\.\\~\\:]", ""));
			}
			else System.out.println("[NOTICE] There was a non-valid stagename for Movie ID " + currentMovieID + ", so it was set to NULL.");
		}
	}
	
	public void pushToDB(DatabaseHelper db, HashMap<String, Star> xmlStars, HashMap<String, String> xmlMovies, HashMap<String, Integer> newDBStars, HashMap<String, Integer> dbMovieIds, HashSet<Integer> existingIds)
	{
		try
		{
			PreparedStatement statement = db.prepareStatement("INSERT INTO stars_in_movies(star_id, movie_id) VALUES (?, ?);");//SELECT (SELECT stars.id FROM stars WHERE stars.first_name = ? AND stars.last_name = ?), movies.id FROM movies WHERE movies.title = ?");
			int counter = 0;
			int total_counter = 0;
			
			for (String xmlMovieId : casts.keySet()) // Note: casts is a HashMap containing Key,Value of (xmlMovieId, List<StarStageName>). DOES NOT CONTAIN
			{                                         // FIRST/LAST NAME!! So that's why we have to use xmlStars, which maps (stageName, StarObject).
				int star_id = -1;
				String movie_title = xmlMovies.get(xmlMovieId);
				
				if (movie_title == null || movie_title.equals("") || existingIds.contains(dbMovieIds.get(movie_title)))
				{
					continue; // Sanity check
				}
				else
				{
					statement.setInt(2, dbMovieIds.get(movie_title.toLowerCase()).intValue());

					for (String star : casts.get(xmlMovieId))
					{
						Star currentStar = xmlStars.get(star); 

						if (currentStar == null)
						{
							System.out.println("[NOTICE] Star w/ Stage Name {" + star + "} does not have a valid first and/or family name, and cannot be added to the movie's {" + movie_title + "} cast list.");
						}
						else if (!currentStar.hasFullName())
						{
							System.out.println("[NOTICE] Star w/ Stage Name {" + currentStar.getStageName() + "} does not have a valid first and/or family name, and cannot be added to the movie's {" + movie_title + "} cast list.");
						}
						else
						{
							if (newDBStars.containsKey(currentStar.getFullName().toLowerCase()))
							{
								star_id = newDBStars.get(currentStar.getFullName().toLowerCase());
								statement.setInt(1, star_id);
								statement.addBatch();
								total_counter++;
								if (++counter == 1000)
								{
									System.out.println("[INFO] Executing batch of 1000 statements... (Current # = " + total_counter + ")");
									statement.executeBatch(); 
									System.out.println("[INFO] Executed.");
									counter = 0;
								}
							}
						}
					}
				}
			}
			System.out.println("[INFO] Executing final batch of statements...");
			statement.executeBatch(); // Execute the rest of the batch.
			System.out.println("[INFO] Executed.\n[INFO] Now commiting new Casts information to the database. This may take a while.  (Total # = " + total_counter + ")");
			db.commit();
			System.out.println("[INFO] New Casts information has been commited to the database."); // (Total # = 23200) out of 47245 :\
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}