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
				//browse logic here
				PrintWriter out = response.getWriter();
			    out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" +
			                "<H1 ALIGN=\"CENTER\">Browse Page</H1>\n\n" +
			                "<H4 ALIGN=\"CENTER\">Browse by Genre</H4>\n");
			    // connect to db
				Connection dbcon;
				dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);	//do we need to connect in every servlet??
				Statement statement = dbcon.createStatement();
				
				//TODO: figure out the layout to make it print prettier
				
				//print out all genres
				/*
				 * NOTES TO MO: I commented the parameters that are not like what is printed to be used in the query in the movielist page
				 */
			    out.println("<p ALIGN=\"CENTER\"><a href=\"./movielist?genre=Action\">Action</a><br>" +
			    		"<a href=\"./movielist?genre=Adventure\">Adventure</a><br>" +
			    		"<a href=\"./movielist?genre=Animation\">Animation</a><br>" +
			    		"<a href=\"./movielist?genre=Arts\">Arts</a><br>" +
			    		"<a href=\"./movielist?genre=Classic\">Classic</a><br>" +
			    		"<a href=\"./movielist?genre=Comedy\">Comedy</a><br>" +
			    		"<a href=\"./movielist?genre=Crime\">Crime</a><br>" +
			    		"<a href=\"./movielist?genre=Detective\">Detective</a><br>" +
			    		"<a href=\"./movielist?genre=Documentary\">Documentary</a><br>" +
			    		"<a href=\"./movielist?genre=Drama\">Drama</a><br>" +
			    		"<a href=\"./movielist?genre=Epics\">Epics/Historical</a><br>" +	//parameter is Epics
			    		"<a href=\"./movielist?genre=Family\">Family</a><br>" +
			    		"<a href=\"./movielist?genre=Fantasy\">Fantasy</a><br>" +
			    		"<a href=\"./movielist?genre=Noir\">Film-Noir</a><br>" +	//parameter is Noir
			    		"<a href=\"./movielist?genre=Foreign\">Foreign</a><br>" +
			    		"<a href=\"./movielist?genre=Gangester\">Gangster</a><br>" +
			    		"<a href=\"./movielist?genre=Horror\">Horror</a><br>" +
			    		"<a href=\"./movielist?genre=Indie\">Indie</a><br>" +
			    		"<a href=\"./movielist?genre=James-Bond\">James Bond</a><br>" +	//parameter is James-Bond
			    		"<a href=\"./movielist?genre=Kid\">Kid</a><br>" +
			    		"<a href=\"./movielist?genre=Love\">Love</a><br>" +
			    		"<a href=\"./movielist?genre=Musical\">Musical</a><br>" +
			    		"<a href=\"./movielist?genre=Mystery\">Mystery</a><br>" +
			    		"<a href=\"./movielist?genre=Roman\">Roman</a><br>" +
			    		"<a href=\"./movielist?genre=Romance\">Romance</a><br>" +
			    		"<a href=\"./movielist?genre=Sci-Fi\">Science Fiction</a><br>" + //parameter is Sci-Fi
			    		"<a href=\"./movielist?genre=Short\">Short</a><br>" +
			    		"<a href=\"./movielist?genre=Spy\">Spy</a><br>" +
			    		"<a href=\"./movielist?genre=Suspense\">Suspense</a><br>" +
			    		"<a href=\"./movielist?genre=Thriller\">Thriller</a><br>" +
			    		"<a href=\"./movielist?genre=Tragedy\">Tragedy</a><br>" +
			    		"<a href=\"./movielist?genre=War\">War</a><br>" +
			    		"<a href=\"./movielist?genre=Western\">Western</a><br></p>");
			    		
			    //print out all title initials
			    out.println();
			    out.println("<H4 ALIGN=\"CENTER\">Browse by Title</H4>\n </BODY>");	   
			    String query = "SELECT left(title,1) from movies group by left(title,1) order by title";
			    ResultSet rs = statement.executeQuery(query);

			    out.println("<p ALIGN=\"CENTER\">");
			    while (rs.next()) 
			    {
			    	out.println("<a href=\"./movielist?title=" + rs.getString(1) + "\">" + rs.getString(1) + "</a><br>");
			    }
			    out.println("</p>");
			    
			    
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
