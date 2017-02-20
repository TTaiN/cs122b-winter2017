import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import recaptcha_helpers.VerifyRecaptcha;
import general_helpers.DatabaseHelper;

@WebServlet("/_dashboard")
public class Dashboard extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public Dashboard() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    	response.setContentType("text/html");
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("employee");
        
        if (username == null)
        {
			request.setAttribute("jsp", true);
            request.getRequestDispatcher("/jsp/dashboard.jsp").include(request, response);
        }
        else 
        {
        	response.sendRedirect("./dashboardMain");
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
   	 	HttpSession session = request.getSession(); 
   	 	try
   	 	{
   	 		DatabaseHelper db = new DatabaseHelper();

   	 		String email = request.getParameter("email");
   	 		String pwd = request.getParameter("pwd");
   	 		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
   	 		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
   	 		ResultSet rs = db.executePreparedStatement("SELECT * from employees where email = '" + email + "'" + " and password = '" + pwd + "'");
   	 		
   	 		if (verify == false)
   		 	{
   			 	request.setAttribute("error", "Missed Captcha.");
   			 	request.setAttribute("jsp", true);
   			 	request.getRequestDispatcher("./jsp/dashboard.jsp").include(request, response);
   		 	}
   	 		else if (rs.next())
			{
				 session.setAttribute("employee", email);
		   		 rs.close();
		   		 db.closeConnection();
				 response.sendRedirect("./dashboardMain");
			}
			else
			{
				 request.setAttribute("error", "Invalid e-mail and/or password.");
				 request.setAttribute("jsp", true);
				 request.getRequestDispatcher("./jsp/dashboard.jsp").include(request, response);
			}
			 rs.close();
			 db.closeConnection();
		 }
		 catch (Exception e)
		 { 			
			ArrayList<String> messages = new ArrayList<String>();
			messages.add("There was an error when trying to login.");
			request.setAttribute("reason", "Login");
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("./jsp/error.jsp").include(request, response);
		 }
	}
}
