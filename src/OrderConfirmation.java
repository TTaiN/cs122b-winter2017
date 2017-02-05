import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/orderConfirmation")

public class OrderConfirmation extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public OrderConfirmation() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null || session.getAttribute("confirmation") == null || session.getAttribute("cart") == null)
		{
			response.sendRedirect("./login");
			return;
		}
		
		request.setAttribute("jsp", true);
		request.getRequestDispatcher("./jsp/order_confirmation.jsp").include(request, response);
		session.removeAttribute("confirmation");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
