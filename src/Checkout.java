import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import ecommerce_helpers.ShoppingCart;
import ecommerce_helpers.OrderDB;

@WebServlet("/checkout")
public class Checkout extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Checkout() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		
		if (username == null || cart == null || cart.isEmpty())
		{
			response.sendRedirect("./login");
		}
		else
		{
			request.setAttribute("jsp", true);
			request.getRequestDispatcher("./jsp/checkout.jsp").include(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		Integer userId = (Integer) session.getAttribute("userId");

		if (username == null || userId == null || cart == null || cart.isEmpty())
		{
			response.sendRedirect("./login");
			return;
		}
				
		ArrayList<String> messages = new ArrayList<String>();
		String number = request.getParameter("number");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String date = request.getParameter("date");
		
		try
		{
			OrderDB newOrder = new OrderDB(number, firstName, lastName, date);
			if (newOrder.validateInfo(messages)) // if order has valid info
			{
				cart.setCustomerInformation(number, firstName, lastName, date);
				newOrder.submitOrders(userId, cart);
				session.setAttribute("confirmation", true);
				response.sendRedirect("./orderConfirmation");
			}
			else // order doesnt have valid info
			{
				request.setAttribute("reason", "Checkout");
				request.setAttribute("messages", messages);
				request.getRequestDispatcher("./jsp/error.jsp").include(request, response);
			}
			newOrder.close();
			return;
		}
		catch (SQLException e)
		{
			messages.add(e.getMessage());
			request.setAttribute("reason", "Checkout");
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("./jsp/error.jsp").include(request, response);	
		}
	}
}
