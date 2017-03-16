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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		if (username == null )
		{
            response.sendRedirect("./login");
            return;
		}
		
		DatabaseHelper db;
		try {
			db = new DatabaseHelper(false);
			Connection conn = db.getConnection();
			String query = "SELECT left(title,1) from movies group by left(title,1) order by title";
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			ArrayList<String> initials = new ArrayList<String>();
			
			while (rs.next()) 
			{
				initials.add(rs.getString(1));
			}
			request.setAttribute("initials", initials);
			
			ArrayList<String> genres = new ArrayList<String>();
			query = "SELECT * from genres g where exists (select (1) from genres_in_movies where genre_id = g.id) order by name;";
			statement = conn.prepareStatement(query);
			rs = statement.executeQuery();
			
			while (rs.next()) 
			{
				genres.add(rs.getString(2));
			}
			request.setAttribute("genres", genres);
			request.getRequestDispatcher("./jsp/browse.jsp").include(request, response);
			db.closeConnection();  //close before or after sending?
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
