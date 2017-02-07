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
			return;
		}
		else if (session.getAttribute("cart") == null)
		{
			ShoppingCart newCart = new ShoppingCart();
			session.setAttribute("cart", newCart);
		}
		
		request.setAttribute("jsp", true);
		request.getRequestDispatcher("./jsp/cart.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String action = request.getParameter("action");
		
		if (username == null)
		{
			response.sendRedirect("./login");
			return;
		}
		else if (session.getAttribute("cart") == null)
		{
			ShoppingCart newCart = new ShoppingCart();
			session.setAttribute("cart", newCart);
		}
		
		if (action != null)
		{
			ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
			
			if (action.equals("Add to Cart"))
			{
				addToCartHandler(cart, request);
			}
			else if (action.equals("Add More to Cart"))
			{
				addMoreToCartHandler(cart, request);
			}
			else if (action.equals("Update Quantity"))
			{
				updateQuantityHandler(cart, request);
			}
			else if (action.equals("Remove"))
			{
				removeFromCartHandler(cart, request);
			}
		}
		
		doGet(request, response);
	}
	
	protected void addToCartHandler(ShoppingCart cart, HttpServletRequest request)
	{
		Integer movie_id = Integer.parseInt(request.getParameter("movie_id"));
		
		if (cart.contains(movie_id))
		{
			request.setAttribute("notice", "[NOTICE] The item <a href='./movie?id=" + movie_id + "'>\"" + cart.getMovie(movie_id).getTitle() + "\"</a> already exists in your cart.");
		}
		else
		{
			try
			{
				Integer quantity;
				try
				{
					quantity = Integer.parseInt(request.getParameter("quantity"));
				}
				catch (NumberFormatException e)
				{
					request.setAttribute("notice", "[NOTICE] Please enter a valid integer from 0 to 500.");
					return;
				}
				
				if (quantity <= 0)
				{
					request.setAttribute("notice", "[NOTICE] You cannot add 0 or negative of an item to your cart. You must add 1 or more quantity.");
				}
				else if (quantity > 500)
				{
					request.setAttribute("notice", "[NOTICE] You cannot buy more than 500 copies of a single item.");
				}
				
				MovieViewDB db = new MovieViewDB();
				Movie movie = db.getCompleteMovie(movie_id);
				db.close();			
				cart.addToCart(movie, quantity);
				request.setAttribute("notice", "[NOTICE] The item <a href='./movie?id=" + movie_id + "'>\"" + movie.getTitle() + "\"</a> was added to your cart! (Quantity: " + quantity + ")");
			}
			catch (SQLException e)
			{
				request.setAttribute("notice", "[NOTICE] There was an error when trying to add the item to your cart.");
			}
		}
	}
	
	protected void addMoreToCartHandler(ShoppingCart cart, HttpServletRequest request)
	{
		Integer movie_id = Integer.parseInt(request.getParameter("movie_id"));
		
		if (!cart.contains(movie_id))
		{
			addToCartHandler(cart, request); // adding more of nothing basically means adding something to your cart, right?
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
				request.setAttribute("notice", "[NOTICE] Please enter a valid integer from 0 to 500.");
				return;
			}
			
			Movie movie = cart.getMovie(movie_id);
			if (quantity <= 0)
			{
				request.setAttribute("notice", "[NOTICE] You cannot add 0 or negative of an item to your cart. You must add 1 or more quantity.");
			}
			else if (((movie.getQuantity()) + quantity) > 500)
			{
				request.setAttribute("notice", "[NOTICE] You cannot buy more than 500 copies of a single item.");
			}	
			else request.setAttribute("notice", "[NOTICE] The quantity of <a href='./movie?id=" + movie_id + "'>\"" + movie.getTitle() + "\"</a> is now " + (quantity + movie.getQuantity()) + ". (Previous: " + cart.updateQuantity(movie_id, (quantity + movie.getQuantity())) + ", you added " + quantity + ").");			
		}
	}
	
	protected void removeFromCartHandler(ShoppingCart cart, HttpServletRequest request)
	{
		Integer movie_id = Integer.parseInt(request.getParameter("movie_id"));
		if (!cart.contains(movie_id))
		{
			request.setAttribute("notice", "[NOTICE] You do not have that item in your cart.");
		}
		else
		{
			String movieTitle = cart.getMovie(movie_id).getTitle();
			cart.removeMovie(movie_id);
			request.setAttribute("notice", "[NOTICE] The item <a href='./movie?id=" + movie_id + "'>\"" + movieTitle + "\"</a> was removed from your cart.");
		}
	}
	
	protected void updateQuantityHandler(ShoppingCart cart, HttpServletRequest request)
	{
		Integer movie_id = Integer.parseInt(request.getParameter("movie_id"));
		if (!cart.contains(movie_id))
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
				request.setAttribute("notice", "[NOTICE] Please enter a valid integer from 0 to 500.");
				return;
			}
			
			Movie movie = cart.getMovie(movie_id);
			
			if (quantity < 0)
			{
				cart.removeMovie(movie_id);
				request.setAttribute("notice", "[NOTICE] The quantity of <a href='./movie?id=" + movie_id + "'>\"" + movie.getTitle() + "\"</a> was set to negative, so it was removed.");
			}
			else if (quantity == 0)
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
