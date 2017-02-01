import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.TopMenu;

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
		    out.println("<H2 ALIGN=\"CENTER\">Welcome to Fabflix!</H2><br>\n" +
		                "<a href=\"./search\"><H4 ALIGN=\"CENTER\">Click to Search</H4>\n" +
		                "<a href=\"./browse\"><H4 ALIGN=\"CENTER\">Click to Browse</H4></a>\n</body>");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
