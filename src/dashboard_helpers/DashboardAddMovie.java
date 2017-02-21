package dashboard_helpers;

import java.io.IOException;
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
			db = new DatabaseHelper();

			String query = "call add_movie ('" + request.getParameter("movieId") + "','" + request.getParameter("title") + "','" +request.getParameter("year") + "','" + request.getParameter("director") + "','" + request.getParameter("bannerUrl") + "','" + request.getParameter("trailerUrl") + "','" + request.getParameter("starId") + "',\"" + request.getParameter("firstName") + "\", '" + request.getParameter("lastName")+ "', '" + request.getParameter("dob") + "', '" + request.getParameter("photoUrl") + "','" + request.getParameter("genreId") + "','" + request.getParameter("genreName")+ "');";
			query = query.replaceAll("''", "NULL");
			System.out.println(query);	//debug
			ResultSet rs = db.executeSQL(query); 
			
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
