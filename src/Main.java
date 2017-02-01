
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;



/**
 * Servlet implementation class Main
 */

public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if (username == null)
		{
			request.getRequestDispatcher("./login").include(request, response);
		}
		else
		{
			PrintWriter out = response.getWriter();
		    out.println("<BODY BGCOLOR=\"#FDF5E6\"><br><br>\n" +
		                "<H1 ALIGN=\"CENTER\">Main Page</H1><br>\n" +
		                "<H2 ALIGN=\"CENTER\">Welcome to FabFlix</H2><br><br><br>\n" +
		                "<H4 ALIGN=\"CENTER\">Click to Search</H4>\n" +		//link to search here - add hyperlink
		                "<a href=\"./browse\"><H4 ALIGN=\"CENTER\">Click to browse</H4></a>\n </BODY>");
		                
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
