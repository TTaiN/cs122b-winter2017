package dashboard_helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import general_helpers.DatabaseHelper;

public class DashboardAddMovie extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public DashboardAddMovie() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        response.setContentType("text/html");
		HttpSession session = request.getSession();
        String employee = (String) session.getAttribute("employee");
        String username = (String) session.getAttribute("username");
        
		if (employee == null && username == null)
		{
            response.sendRedirect("./_dashboard");
            return;
		}
		else if (employee == null && username != null)
		{
            response.sendRedirect("./main");
            return;
		}
		
		DatabaseHelper db;
		try 
		{
			db = new DatabaseHelper(true);
			Connection conn = db.getConnection();

			String movieId = request.getParameter("movieId");
			String title = request.getParameter("title");
			String year = request.getParameter("year");
			String director = request.getParameter("director");
			String bannerUrl = request.getParameter("bannerUrl");
			String trailerUrl = request.getParameter("trailerUrl");
			String starId = request.getParameter("starId");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String dob = request.getParameter("dob");
			String photoUrl = request.getParameter("photoUrl");
			String genreId = request.getParameter("genreId");
			String genreName = request.getParameter("genreName");
				
			String query = "call add_movie (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(query);
			
			if (movieId.isEmpty()) {
				statement.setNull(1, java.sql.Types.INTEGER);
			} else {
				statement.setInt(1, Integer.parseInt(movieId));
			}
			statement.setString(2, title);
			statement.setString(3, year);
			statement.setString(4, director);
			statement.setString(5, bannerUrl);
			statement.setString(6, trailerUrl);
			if (starId.isEmpty()) {
				statement.setNull(7, java.sql.Types.INTEGER);
			} else {
				statement.setInt(7, Integer.parseInt(starId));
			}
			statement.setString(8, firstName);
			statement.setString(9, lastName);
			statement.setString(10, dob);
			statement.setString(11, photoUrl);
			if (genreId.isEmpty()) {
				statement.setNull(12, java.sql.Types.INTEGER);
			} else {
				statement.setInt(12, Integer.parseInt(genreId));
			}
			statement.setString(13, genreName);
			
			ResultSet rs = statement.executeQuery();
			
			request.setAttribute("notice", "Movie inserted");
    		request.getRequestDispatcher("./jsp/dashboard_main.jsp").include(request, response);
    		
    		db.closeConnection();
		} 
		catch (SQLException e) 
		{
			request.setAttribute("notice", e.getMessage());
    		request.getRequestDispatcher("./jsp/dashboard_main.jsp").include(request, response);	
    		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
