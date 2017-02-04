import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import general_helpers.Movie;
import ecommerce_helpers.ShoppingCart;
import single_view_helpers.MovieViewDB;
import java.sql.SQLException;

@WebServlet("/movie")

public class MovieView extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public MovieView() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession();
				
		if (session.getAttribute("username") == null || request.getParameter("id") == null)
		{
			response.sendRedirect("./login"); // if movie_id null login will redirect back to main
			return;
		}
		
		Integer movie_id = Integer.parseInt(request.getParameter("id"));
		Movie movie = null;
		
		ShoppingCart cart = new ShoppingCart(session);
		
		if (cart.contains(movie_id))
		{
			request.setAttribute("movie", cart.getMovie(movie_id));
		}
		else
		{
			
			try
			{
				MovieViewDB db = new MovieViewDB();
				movie = db.getCompleteMovie(movie_id);
				db.close();
				request.setAttribute("movie", movie);
				System.out.println("Movie Title: " + movie.getTitle());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				response.sendRedirect("./main"); // need to specify behavior when wrong id is entered.
				return;
			}
		}
		request.getRequestDispatcher("./jsp/movie.jsp").include(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
