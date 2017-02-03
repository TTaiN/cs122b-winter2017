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
		    out.println("<H3 ALIGN=\"CENTER\">Welcome to Fabflix!</H3><br>\n" +
		                "<a href=\"./search\"><H2 ALIGN=\"CENTER\">Click to Search</H2>\n" +
		                "<a href=\"./browse\"><H2 ALIGN=\"CENTER\">Click to Browse</H2></a>\n</body>");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
