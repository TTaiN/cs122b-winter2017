package layout_helpers;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import general_helpers.DatabaseHelper;

public class TopMenu
{
	public static void print(PrintWriter out)
	{
		out.println("<br><center><a href='./main'><img src=\"./images/logo.png\" alt=\"logo\"></a></center><br>");
		out.println("<div class='topmenu'>");
		out.println("<table class='topmenu_table'>");
		out.println("<tr>");
		out.println("<td class='topmenu_button'><a href='./main'>Main Page</a></td>");
		out.println("<td class='topmenu_button'><a href='./browse'>Browse</a></td>");
		out.println("<td class='topmenu_button'><a href='./search'>Search</a></td>");
		out.println("<td class='topmenu_button'><a href='./cart'>Cart/Checkout</a></td>");
		out.println("<td class='topmenu_button'><a href='./logout'>Logout</a></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
	}
	
	public static void searchPrint(PrintWriter out)
	{
		out.println("<form>");
		out.println("<input type = 'text' size='30' onkeyup='showResult(this.value)'>");
		out.println("<div id='livesearch'></div>");
		out.println("</form>");
	}
	
	public static String getQuery(ArrayList<String> keywords)
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
	
	public static ArrayList<String> showResult(String str) throws SQLException
	{
		
		ArrayList<String>  result = new ArrayList<String>();
		if(str == null)
			return result;
		
		ArrayList<String> keywords = new ArrayList<String>(Arrays.asList(str.split(" ")));
		String query = getQuery(keywords);
		
		if(query.equals(""))
			return result;
		
		DatabaseHelper db = new DatabaseHelper();
		ResultSet rs = db.executePreparedStatement(query);
		while(rs.next())
		{
			result.add("<span><a href='./movie?id=" +  rs.getString("id") + "'>" + 
					rs.getString("title") + "</a></span><br>");
		}
		return result;
	}
	
	public static void likePredicatePrint(PrintWriter out)
	{
		out.println("<br><center><img src=\"../images/logo.png\" alt=\"logo\"></center><br>");
		out.println("<div class='topmenu'>");
		out.println("<table class='topmenu_table'>");
		out.println("<tr>");
		out.println("<td class='topmenu_button'><a href='../main'>Return to Main Page</a></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
	}
	
	public static void dashboardPrint(PrintWriter out)
	{
		out.println("<br><center><a href='./main'><img src=\"./images/logo.png\" alt=\"logo\"></a></center><br>");
		out.println("<div class='topmenu'>");
		out.println("<table class='topmenu_table'>");
		out.println("<tr>");
		out.println("<td class='topmenu_button'><a href='./logout'>Log out</a></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
	}
}
