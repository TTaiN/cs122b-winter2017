import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import general_helpers.Star;
import java.sql.SQLException;
import single_view_helpers.StarViewDB;

@WebServlet("/star")

public class StarView extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public StarView() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession();

		if (session.getAttribute("username") == null || request.getParameter("id") == null)
		{
			response.sendRedirect("./login");
			return;
		}
		
		Integer star_id = Integer.parseInt(request.getParameter("id"));
		Star star = null;

		try
		{
			StarViewDB db = new StarViewDB();
			star = db.getStar(star_id);
			star.setMovies(db.getMoviesForStar(star_id));
			db.close();
		}
		catch (SQLException e)
		{
			response.sendRedirect("./main"); // need to specify behavior when sql error.
			return;		
		}
		
		request.setAttribute("star", star);
		request.getRequestDispatcher("./jsp/star.jsp").include(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}

