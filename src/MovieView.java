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
import java.util.ArrayList;

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
			response.sendRedirect("./login");
			return;
		}
		
		Integer movie_id = Integer.parseInt(request.getParameter("id"));		
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		
		if (cart != null && cart.contains(movie_id))
		{
			request.setAttribute("movie", cart.getMovie(movie_id));
		}
		else
		{
			try
			{
				MovieViewDB db = new MovieViewDB();
				Movie movie = db.getCompleteMovie(movie_id);
				db.close();
				request.setAttribute("movie", movie);
			}
			catch (SQLException e)
			{
				ArrayList<String> messages = new ArrayList<String>();
				messages.add(e.getMessage());
				request.setAttribute("reason", "Movie");
				request.setAttribute("messages", messages);
				request.getRequestDispatcher("./jsp/error.jsp").include(request, response);
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
