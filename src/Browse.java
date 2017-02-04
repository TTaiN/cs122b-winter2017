/*
 * NOTES TO MO:
 * 	Assuming the movielist servlet link is spelled exactly like that
 * 	Parameters to pass to movielist servlet:
 * 		- genre
 * 		- title	
 * 	Look at the google doc (original genre list in the database, our genre list on browse servlet) to check
 * 		for different cases of genres
 * 		- when doing LIKE query in movielist servlet watch out for scifi variations, james bond (two words), 
 * 			adventure/advanture typo, comedy/commedy typo, lower case, roman vs romance, etc.
 */

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;

import layout_helpers.TopMenu;
import general_helpers.DatabaseHelper;



/**
 * Servlet implementation class Browse
 */

public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Browse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		DatabaseHelper db = null;
		
		if (username == null)
		{
			request.getRequestDispatcher("./login").include(request, response);
		}
		else
		{
			try {
				db = new DatabaseHelper();
				PrintWriter out = response.getWriter();
				out.println("<html><head><link rel='stylesheet' type='text/css' href='./style/browse.css'/><title>Browse</title></head>");
			    out.println("<BODY>");
			    TopMenu.print(out);
			    out.println("<div class=\"wrapper\">");

				//Browse by title
			    out.println();
			    out.println("<br><H1>Browse by Title</H1>\n </BODY>");	   
			    String query = "SELECT left(title,1) from movies group by left(title,1) order by title";
			    ResultSet rs = db.executeSQL(query);

			    out.println("<p ALIGN=\"CENTER\">");
			    while (rs.next()) 
			    {
			    	out.println("<a href=\"./movielist?firstchar=" + rs.getString(1) + "\">" + rs.getString(1) + "</a>");
			    }
			    out.println("</p></div>");
				
			    //Browse by genre
			    out.println("<br><H1>Browse by Genre</H1>\n");
			    out.println("<div class=\"wrapper\"><div id=\"cols\"><ul style=\"list-style: none;\">" + 
				 
			    		"<li><a href=\"./movielist?genre=Action\">Action</a></li>" +
			    		"<li><a href=\"./movielist?genre=Adv nture\">Adventure</a></li>" +
			    		"<li><a href=\"./movielist?genre=Animation\">Animation</a></li>" +
			    		"<li><a href=\"./movielist?genre=Arts\">Arts</a></li>" +
			    		"<li><a href=\"./movielist?genre=Classic\">Classic</a></li>" +
			    		"<li><a href=\"./movielist?genre=Com edy\">Comedy</a></li>" +
			    		"<li><a href=\"./movielist?genre=Crime\">Crime</a></li>" +
			    		
			    		"</ul></div><div id=\"cols\"><ul style=\"list-style: none;\">" +		    		

			    		"<li><a href=\"./movielist?genre=Documentary\">Documentary</a></li>" +
			    		"<li><a href=\"./movielist?genre=Drama\">Drama</a></li>" +
			    		"<li><a href=\"./movielist?genre=Epics\">Epics/Historical</a></li>" +
			    		"<li><a href=\"./movielist?genre=Family\">Family</a></li>" +
			    		"<li><a href=\"./movielist?genre=Fantasy\">Fantasy</a></li>" +
			    		"<li><a href=\"./movielist?genre=Foreign\">Foreign</a></li>" +
			    		"<li><a href=\"./movielist?genre=Gangster\">Gangster</a></li>" +
			    		
						"</ul></div><div id=\"cols\"><ul style=\"list-style: none;\">" +	
						
						"<li><a href=\"./movielist?genre=Horror\">Horror</a></li>" +
			    		"<li><a href=\"./movielist?genre=Indie\">Indie</a></li>" +
			    		"<li><a href=\"./movielist?genre=Bond\">James Bond</a></li>" +
			    		"<li><a href=\"./movielist?genre=Musical\">Musical</a></li>" +
			    		"<li><a href=\"./movielist?genre=Mystery\">Mystery</a></li>" +
			    		"<li><a href=\"./movielist?genre=Roman\">Romance</a></li>" +
			    		"<li><a href=\"./movielist?genre=Sci\">Science Fiction</a></li>" + 

						"</ul></div><div id=\"cols\"><ul style=\"list-style: none;\">" +		    		

						"<li><a href=\"./movielist?genre=Spy\">Spy</a></li>" +
			    		"<li><a href=\"./movielist?genre=Suspense\">Suspense</a></li>" +
			    		"<li><a href=\"./movielist?genre=Thriller\">Thriller</a></li>" +
			    		"<li><a href=\"./movielist?genre=War\">War</a></li>" +
			    		
			    		"</ul></div></div></div></body>");
			    
			} catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					db.closeConnection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
