import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/search")
public class Search extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Search() 
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
			request.setAttribute("jsp", true);
			request.getRequestDispatcher("./jsp/search.jsp").include(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
