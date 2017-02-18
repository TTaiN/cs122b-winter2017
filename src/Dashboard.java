

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.ResultSetMetaData;

import layout_helpers.TopMenu;
import recaptcha_helpers.VerifyRecaptcha;
import general_helpers.DatabaseHelper;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	response.setContentType("text/html");
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
   	 	HttpSession session = request.getSession(); 
   	 	try
   	 	{
   	 		DatabaseHelper db = new DatabaseHelper();

   	 		String email = request.getParameter("email");
   	 		String pwd = request.getParameter("pwd");
   	 		//String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
   	 		//boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
   	 		ResultSet rs = db.executePreparedStatement("SELECT * from employees where email = '" + email + "'" + " and password = '" + pwd + "'");
//   		 if( verify == false)
//   		 {
//   			 request.setAttribute("error", "Missed Captcha.");
//   			 request.setAttribute("jsp", true);
//   			 request.getRequestDispatcher("./jsp/login.jsp").include(request, response);
//   		 }
   	 		
			 if (rs.next())
			 {
				 System.out.println("login successful");
				 session.setAttribute("username", email);
//				 session.setAttribute("fu", rs.getInt("id"));
		   		 rs.close();
		   		 db.closeConnection();
				 response.sendRedirect("./dashboardMain");
		   		//request.getRequestDispatcher("./dashboardMain").include(request, response);
			 }
			 else
			 {
				 System.out.println("cant login");
				 request.setAttribute("error", "Invalid e-mail and/or password.");
				 request.setAttribute("jsp", true);
				 request.getRequestDispatcher("./jsp/dashboard.jsp").include(request, response);
			 }
		
			 System.out.println("all good");
			 rs.close();
			 db.closeConnection();
		 }
		 catch (Exception e)
		 { 			
			 System.out.println(e.getMessage());
			ArrayList<String> messages = new ArrayList<String>();
			messages.add("There was an error when trying to login.");
			request.setAttribute("reason", "Login");
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("./jsp/error.jsp").include(request, response);
		 }
	}

}
