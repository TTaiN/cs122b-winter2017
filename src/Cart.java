import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import util.Movie;

import single_view_helpers.MovieViewDB;
import ecommerce_helpers.ShoppingCart;

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
		
		ShoppingCart cart = new ShoppingCart(session);
				
		if (!cart.exists())
		{
			/* Testing Only */
			LinkedHashMap<Integer, Movie> newCart = new LinkedHashMap<Integer, Movie>(); 
			session.setAttribute("cart", newCart);
			cart.setCart(newCart);
			try
			{
				MovieViewDB db = new MovieViewDB();
				Movie test = db.getMovie(135004);
				test.setQuantity(3);
				Movie test2 = db.getMovie(326006);
				test2.setQuantity(4);
				newCart.put(135004, test);
				newCart.put(326006, test2);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		request.getRequestDispatcher("./jsp/cart.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		doGet(request, response);
	}
}
