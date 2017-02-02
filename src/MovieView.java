import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
		String username = (String) session.getAttribute("username");
		Integer movie_id = Integer.parseInt(request.getParameter("id"));
		
		if (username == null || movie_id == null)
		{
			response.sendRedirect("./login");
		}
		else
		{
			request.getRequestDispatcher("./jsp/movie.jsp").include(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
