import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ecommerce_helpers.ShoppingCart;

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
		ShoppingCart cart = new ShoppingCart(session);
		
		if (username == null || cart.isEmpty())
		{
			response.sendRedirect("./login");
			return;
		}
		else
		{
			request.setAttribute("jsp", true);
			request.getRequestDispatcher("./jsp/checkout.jsp").include(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		printParameters(request, response);
		//doGet(request, response);
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

}
