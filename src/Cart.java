import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.sql.SQLException;
import java.io.IOException;

import single_view_helpers.MovieViewDB;
import ecommerce_helpers.ShoppingCart;
import general_helpers.Movie;

@WebServlet("/cart")

public class Cart extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public Cart() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if (username == null)
		{
			response.sendRedirect("./login");
		}
		request.getRequestDispatcher("./jsp/cart.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if (username == null)
		{
			response.sendRedirect("./login");
		}
		else if (request.getParameter("action") != null)
		{
			ShoppingCart cart = new ShoppingCart(session);
			if (request.getParameter("action").equals("Add to Cart"))
			{
				addToCartHandler(cart, request);
			}
			else if (request.getParameter("action").equals("Update Quantity"))
			{
				updateQuantityHandler(cart, request);
			}
		}
		
		doGet(request, response);
	}
	
	protected void printParameters(HttpServletRequest request, HttpServletResponse response)
	{
		for (String parameter : request.getParameterMap().keySet())
		{
			try
			{
				response.getWriter().println("Parameter |" + parameter + "|:" + request.getParameter(parameter));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	protected void addToCartHandler(ShoppingCart cart, HttpServletRequest request)
	{
		Integer movie_id = Integer.parseInt(request.getParameter("movie_id"));
		if (!cart.exists())
		{
			LinkedHashMap<Integer, Movie> newCart = new LinkedHashMap<Integer, Movie>();
			request.getSession().setAttribute("cart", newCart);
			cart.setCart(newCart);
		}

		if (cart.contains(movie_id))
		{
			request.setAttribute("notice", "[NOTICE] The item <a href='./movie?id=" + movie_id + "'>\"" + cart.getMovie(movie_id).getTitle() + "\"</a> already exists in your cart.");
		}
		else
		{
			try
			{
				MovieViewDB db = new MovieViewDB();
				Movie movie = db.getMovie(movie_id);
				movie.setStars(db.getStarsForMovie(movie_id));
				db.close();
				Integer quantity = Integer.parseInt(request.getParameter("quantity"));
				cart.addToCart(movie, quantity);
				request.setAttribute("notice", "[NOTICE] The item <a href='./movie?id=" + movie_id + "'>\"" + movie.getTitle() + "\"</a> was added to your cart! (Quantity: " + quantity + ")");
			}
			catch (SQLException e)
			{
				request.setAttribute("notice", "[NOTICE] There was a database error when trying to add the item to your cart.");
			}
		}
	}
	
	protected void updateQuantityHandler(ShoppingCart cart, HttpServletRequest request)
	{
		Integer movie_id = Integer.parseInt(request.getParameter("movie_id"));
		if (!cart.exists() || !cart.contains(movie_id))
		{
			request.setAttribute("notice", "[NOTICE] You don't have this item in your cart, so updating the quantity failed.");
		}
		else
		{
			Integer quantity;
			
			try
			{
				quantity = Integer.parseInt(request.getParameter("quantity"));
			}
			catch (NumberFormatException e)
			{
				request.setAttribute("notice", "[NOTICE] You cannot buy more than 500 copies of a single item.");
				return;
			}
			
			Movie movie = cart.getMovie(movie_id);
			if (quantity == 0)
			{
				cart.removeMovie(movie_id);
				request.setAttribute("notice", "[NOTICE] The quantity of <a href='./movie?id=" + movie_id + "'>\"" + movie.getTitle() + "\"</a> was set to 0, so it was removed.");
			}
			else if (quantity > 500)
			{
				request.setAttribute("notice", "[NOTICE] You cannot buy more than 500 copies of a single item.");
			}
			else request.setAttribute("notice", "[NOTICE] The quantity of <a href='./movie?id=" + movie_id + "'>\"" + movie.getTitle() + "\"</a> is now " + quantity + ". (Previous: " + cart.updateQuantity(movie_id, quantity) + ")");
		}
	}
}
