import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/logout")
public class Logout extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public Logout() 
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
        else 
        {
        	session.invalidate();
        	request.setAttribute("jsp", true);
        	request.setAttribute("error", "You have successfully logged out."); // not really an error, but hey, it works
        	request.getRequestDispatcher("./jsp/login.jsp").include(request, response);
        	return;
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
