import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorsParser extends DefaultHandler
{
	private ArrayList<Star> stars;
	private HashMap<String, Star> starsMap;
	private Star currentStar;
	
	private String tempVal;
	
	public ActorsParser()
	{
		stars = new ArrayList<Star>();
		starsMap = new HashMap<String, Star>();
	}
	
	public HashMap<String, Star> runParser() // Change to run() later.
	{
		parseDocument();
		return starsMap;
	}

	private void parseDocument() 
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try 
		{
			SAXParser sp = spf.newSAXParser();
			sp.parse("actors63.xml", this);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void printData()
	{	
		int counter = 1;
		Iterator<Star> it = stars.iterator();
		
		while(it.hasNext()) 
		{
			Star current = it.next();
			System.out.println("[#" + counter++ + "] {" + current.getStageName() + "} " + current.getFirstName() + "|" + current.getLastName() + " (Date of Birth: " + current.getDOB() + ")");
		}
		
		System.out.println("\n[INFO] Numbers of Stars: " + stars.size() + ".");
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		tempVal = new String(ch,start,length);
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
		tempVal = "";
		if(qName.equalsIgnoreCase("actor")) 
		{
			currentStar = new Star();
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException 
	{

		if(qName.equalsIgnoreCase("actor"))
		{
			stars.add(currentStar);
			starsMap.put(currentStar.getStageName(), currentStar);
		}
		else if (qName.equalsIgnoreCase("stagename")) // Stage Name
		{
			if (!tempVal.equals(""))
			{
				currentStar.setStageName(tempVal.replaceAll("[^A-Za-z \\.\\~\\:]", ""));
			}
		}
		else if (qName.equalsIgnoreCase("firstname")) // First Name (sometimes null, so Full Name parsing is required.)
		{
			if (!tempVal.equals(""))
			{
				currentStar.setFirstName(tempVal.replaceAll("[^A-Za-z \\.\\~\\:]", ""));
			}
			else
			{
				System.out.println("[NOTICE] Star with Stage Name " + currentStar.getStageName() + " does not have a valid first name. Setting to NULL.");
			}
			/*
			else
			{
				String[] temp = currentStar.getStageName().split(" ");
				if (temp.length == 2)
				{
					currentStar.setFirstName(temp[0]);
				}
				else if (temp.length > 2)
				{
					StringBuilder firstName = new StringBuilder();
					for (int i = 0; i != temp.length-1; i++)
					{
						firstName.append(temp[i]);
						firstName.append(" ");
					}
					currentStar.setFirstName(firstName.toString());
				}
			}
			*/
		}
		else if (qName.equalsIgnoreCase("familyname")) // Last Name (sometimes null, so Full Name parsing is required.)
		{
			if (!tempVal.equals(""))
			{
				currentStar.setLastName(tempVal.replaceAll("[^A-Za-z \\.\\~\\:]", ""));
			}
			else
			{
				System.out.println("[NOTICE] Star with Stage Name " + currentStar.getStageName() + " does not have a valid last name. Setting to NULL.");
			}
			/*
			else
			{
				String[] temp = currentStar.getStageName().split(" ");
				currentStar.setLastName(temp[temp.length-1]);
			}
			*/

		}
		else if (qName.equalsIgnoreCase("dob")) // Last Name (sometimes null, so Full Name parsing is required.)
		{
			if (!tempVal.equals(""))
			{
				try
				{
					Integer year = Integer.parseInt(tempVal.replaceAll("[^//d]", ""));
					currentStar.setDOB(String.valueOf(year));
				}
				catch (Exception e)
				{
					System.out.println("[NOTICE] Star with Stage Name " + currentStar.getStageName() + " does not have a valid DOB. Setting to NULL.");
				}
			}
		}
	}
	

	public HashMap<String, Integer> pushToDB(DatabaseHelper db, HashSet<String> dbStars)
	{
		HashSet<String> starsXml = new HashSet<String>();
		try
		{
			PreparedStatement statement = db.prepareStatement("insert into stars(id, first_name, last_name, dob, photo_url) values (NULL, ?, ?, ?, NULL)");
			Iterator<Star> it = stars.iterator();
			int counter = 0;
			
			while (it.hasNext())
			{
				Star current = it.next();
				if (current.hasFullName())
				{
					if (!dbStars.contains(current.getFullName().trim().toLowerCase()) && 
						!starsXml.contains(current.getFullName().trim().toLowerCase()) &&
						!dbStars.contains(current.getFullName().toLowerCase()))
					{
						starsXml.add(current.getFullName().trim().toLowerCase());
						statement.setString(1, current.getFirstName());
						statement.setString(2, current.getLastName());
						if (current.getDOB().equals("NULL"))
						{
							statement.setString(3, null);
						}
						else statement.setString(3, current.getDOB() + "-11-11");
						statement.addBatch();
						
						if (++counter == 1000)
						{
							System.out.println("[INFO] Executing batch of 1000 statements...");
				            statement.executeBatch(); // Execute the rest of the batch.
				            System.out.println("[INFO] Executed.");
				            counter = 0;
						}
						
					}
					else System.out.println("[NOTICE] Star with Full Name {" + current.getFullName() + "} already exists in the database. Skipping add to database.");
				}
				else System.out.println("[NOTICE] Star with Stage Name {" + current.getStageName() + "} did not have a first and/or last name. Skipping add to database.");
			}
			System.out.println("[INFO] Executing batch of final statements...");
            statement.executeBatch(); // Execute the rest of the batch.
            System.out.println("[INFO] Executed.");
            System.out.println("[INFO] Now committing new Star information to the database. This could take a while.");
            db.commit();
	        System.out.println("[INFO] New Star information has been commited.");
	        
	        /* Get all the new star information from the DB for Casts database handling. */
			HashMap<String, Integer> newDBStars = new HashMap<String, Integer>();
			ResultSet rs = db.executeSQL("Select id, first_name, last_name from stars");
			while(rs.next())
			{
				newDBStars.put(rs.getString("first_name") + " " + rs.getString("last_name"), rs.getInt("id"));
			}
			return newDBStars;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}



