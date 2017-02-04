import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import layout_helpers.TopMenu;

public class Main extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Main() 
    {
        super();
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if (username == null)
		{
			response.sendRedirect("./login");
		}
		else
		{
			
			PrintWriter out = response.getWriter();
		    out.println("<html><head><link rel='stylesheet' type='text/css' href='./style/main.css'/><title>Main</title></head><body>");
		    TopMenu.print(out);
		    out.println("<H2 ALIGN=\"CENTER\"><a href=\"./search\">Search for movies</a>   <img src=\"./images/searchlogo.png\" style=\"height:18px\"></H2><h4 align=\"center\">- or -</h4><body>");
		    request.setAttribute("inMain", true);
		    request.getRequestDispatcher("/browse").include(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
