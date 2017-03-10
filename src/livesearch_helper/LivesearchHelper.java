package livesearch_helper;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import general_helpers.DatabaseHelper;

public class LivesearchHelper {
	private static String getQuery(ArrayList<String> keywords)
	{
		String query = "SELECT id, title FROM movies WHERE MATCH(title) AGAINST ('";
		int sz = keywords.size();
		
		if(sz == 0)
			return "";
		
		for(int i = 0; i < sz; i++){
			if(i < sz-1)
				query += "+" + keywords.get(i) + " ";
			else
				query += "+" + keywords.get(i) + "*' IN BOOLEAN MODE)";
		}
		return query;
	}
	
	private static ArrayList<String> showResult(String str) throws SQLException
	{
		
		ArrayList<String>  result = new ArrayList<String>();
		if(str == null)
			return result;
		
		ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(str.split(" ")));
		String query = getQuery(keywords);
		

		DatabaseHelper db = new DatabaseHelper();
		ResultSet rs = db.executePreparedStatement(query);
		
		if(!rs.next())
			return result;
		
		rs.beforeFirst();
		result.add("<ul class='results' >");
		while(rs.next())
		{
			result.add("<li><a href='./movie?id=" +  rs.getString("id") + "'><br /><span>" + 
					rs.getString("title") + "</span></a></li>");
		}
		result.add("</ul>");
		return result;
	}
	
	public static void printResult(PrintWriter out, String query) throws SQLException
	{
		ArrayList<String> results = LivesearchHelper.showResult(query);
		for( int i = 0; i < 10 && i < results.size(); i++)
		{
		    out.println(results.get(i));
		}
	}
	

}
