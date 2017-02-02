import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ecommerce_helpers.ShoppingCart;
import util.Movie;
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
		ShoppingCart cart = new ShoppingCart(session);
		Movie movie = null;
		
		if (cart.getQuantity(movie_id) == 0) // movie isnt in cart
		{
			try
			{
				MovieViewDB db = new MovieViewDB();
				movie = db.getMovie(movie_id);
				movie.setStars(db.getStarsForMovie(movie_id));
				db.close();
			}
			catch (SQLException e)
			{
				response.sendRedirect("./main"); // need to specify behavior when wrong id is entered.
				return;
			}
		}
		else movie = cart.getMovie(movie_id); // cached, movie already in cart, performance optimization
		
		request.setAttribute("movie", movie);
		request.getRequestDispatcher("./jsp/movie.jsp").include(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
