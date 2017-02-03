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



/**
 * Servlet implementation class Browse
 */

public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String loginUser = "cs122b";
    String loginPasswd = "cs122bgroup42";
    String loginUrl = "jdbc:mysql://35.167.240.46/moviedb";
       
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
		
		if (username == null)
		{
			request.getRequestDispatcher("./login").include(request, response);
		}
		else
		{
			try {
				 // connect to db
				Connection dbcon;
				dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);	//do we need to connect in every servlet??
				// @Yolie: i get this error sometimes http://ttain.tk/upload/eclipse_2017-02-01_00-11-03.png
				Statement statement = dbcon.createStatement();
				
				PrintWriter out = response.getWriter();
				out.println("<html><head><link rel='stylesheet' type='text/css' href='./style/main.css'/><title>Browse</title></head>");
			    out.println("<BODY>");
			    TopMenu.print(out);
			    out.println("<H1 ALIGN=\"CENTER\">Browse</H1>\n\n");

				//Browse by title
			    out.println();
			    out.println("<br><H2 ALIGN=\"CENTER\">Browse by Title</H2>\n </BODY>");	   
			    String query = "SELECT left(title,1) from movies group by left(title,1) order by title";
			    ResultSet rs = statement.executeQuery(query);

			    out.println("<p ALIGN=\"CENTER\">");
			    while (rs.next()) 
			    {
			    	out.println("<a href=\"./movielist?title=" + rs.getString(1) + "\">" + rs.getString(1) + "</a> ");
			    }
			    out.println("</p></div>");
				
			    //Browse by genre
				/*
				 * NOTES TO MO: I commented the parameters that are not like what is printed to be used in the query in the movielist page
				 */
			    out.println("<br><H2 ALIGN=\"CENTER\">Browse by Genre</H2>\n");
			    out.println("<div style=\"float: left; width: 25%;\"><ul style=\"list-style: none;\">" + 
				 
			    		"<li><a href=\"./movielist?genre=Action\">Action</a></li>" +
			    		"<li><a href=\"./movielist?genre=Adventure\">Adventure</a></li>" +
			    		"<li><a href=\"./movielist?genre=Animation\">Animation</a></li>" +
			    		"<li><a href=\"./movielist?genre=Arts\">Arts</a></li>" +
			    		"<li><a href=\"./movielist?genre=Classic\">Classic</a></li>" +
			    		"<li><a href=\"./movielist?genre=Comedy\">Comedy</a></li>" +
			    		"<li><a href=\"./movielist?genre=Crime\">Crime</a></li>" +
			    		"<li><a href=\"./movielist?genre=Detective\">Detective</a></li>" +
			    		"<li><a href=\"./movielist?genre=Documentary\">Documentary</a></li>" +
			    		
			    		"</ul></div><div style=\"float: left; width: 25%;\"><ul style=\"list-style: none;\">" +		    		
			    		
			    		"<li><a href=\"./movielist?genre=Drama\">Drama</a></li>" +
			    		"<li><a href=\"./movielist?genre=Epics\">Epics/Historical</a></li>" +	//parameter is Epics
			    		"<li><a href=\"./movielist?genre=Family\">Family</a></li>" +
			    		"<li><a href=\"./movielist?genre=Fantasy\">Fantasy</a></li>" +
			    		"<li><a href=\"./movielist?genre=Noir\">Film-Noir</a></li>" +	//parameter is Noir
			    		"<li><a href=\"./movielist?genre=Foreign\">Foreign</a></li>" +
			    		"<li><a href=\"./movielist?genre=Gangester\">Gangster</a></li>" +
			    		"<li><a href=\"./movielist?genre=Horror\">Horror</a></li>" +
			    		"<li><a href=\"./movielist?genre=Indie\">Indie</a></li>" +
			    		
						"</ul></div><div style=\"float: left; width: 25%;\"><ul style=\"list-style: none;\">" +		    		

			    		"<li><a href=\"./movielist?genre=James-Bond\">James Bond</a></li>" +	//parameter is James-Bond
			    		"<li><a href=\"./movielist?genre=Kid\">Kid</a></li>" +
			    		"<li><a href=\"./movielist?genre=Love\">Love</a></li>" +
			    		"<li><a href=\"./movielist?genre=Musical\">Musical</a></li>" +
			    		"<li><a href=\"./movielist?genre=Mystery\">Mystery</a></li>" +
			    		"<li><a href=\"./movielist?genre=Roman\">Roman</a></li>" +
			    		"<li><a href=\"./movielist?genre=Romance\">Romance</a></li>" +
			    		"<li><a href=\"./movielist?genre=Sci-Fi\">Science Fiction</a></li>" + //parameter is Sci-Fi
			    		"<li><a href=\"./movielist?genre=Short\">Short</a></li>" +

						"</ul></div><div style=\"float: left; width: 25%;\"><ul style=\"list-style: none;\">" +		    		

			    		"<li><a href=\"./movielist?genre=Spy\">Spy</a></li>" +
			    		"<li><a href=\"./movielist?genre=Suspense\">Suspense</a></li>" +
			    		"<li><a href=\"./movielist?genre=Thriller\">Thriller</a></li>" +
			    		"<li><a href=\"./movielist?genre=Tragedy\">Tragedy</a></li>" +
			    		"<li><a href=\"./movielist?genre=War\">War</a></li>" +
			    		"<li><a href=\"./movielist?genre=Western\">Western</a></li>" +
			    		
			    		"</ul></div>");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
