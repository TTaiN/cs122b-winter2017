import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import general_helpers.DatabaseHelper;
import recaptcha_helpers.VerifyRecaptcha;

public class Portal extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    { 
    	response.setContentType("text/html");
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        
        if (username == null)
        {
			request.setAttribute("jsp", true);
            request.getRequestDispatcher("/jsp/portal.jsp").include(request, response);
        }
        else 
        {
        	response.sendRedirect("/fabflix/main");
        }
    }
    
     public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
     {
    	 response.setContentType("text/html");
    	 HttpSession session = request.getSession(); 
    	 
    	 if (request.getParameter("mobile") != null) // Android Application
    	 {
        	 response.setContentType("application/json");

    		 try
    		 {
        		 DatabaseHelper db = new DatabaseHelper(false);
        		 String email = request.getParameter("email");
        		 String pwd = request.getParameter("pwd");
        		 Connection conn = db.getConnection();
        		 String query = "SELECT * from customers where email = ? and password = ?";
        		 PreparedStatement statement = conn.prepareStatement(query);
        	 	 statement.setString(1, email);
        	 	 statement.setString(2, pwd);
       	 		 ResultSet rs = statement.executeQuery();
       	 		 if (rs.next())
        		 {
        			 response.getWriter().print("{login: true, email:\"" + email + "\"}");
        		 }
        		 else response.getWriter().print("{login: false, error:\"\"}");
    		 }
    		 catch (SQLException e)
    		 {
    			 response.getWriter().print("{login: false, error:\"" + e.getMessage() + "\"}");
    		 }
    	 }
    	 else
    	 {
        	 try
        	 {
        		 DatabaseHelper db = new DatabaseHelper(false);
        		 String email = request.getParameter("email");
        		 String pwd = request.getParameter("pwd");
        		 String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        		 boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        		 
        		 Connection conn = db.getConnection();
        		 String query = "SELECT * from customers where email = ? and password = ?";
        		 PreparedStatement statement = conn.prepareStatement(query);
        	 	 statement.setString(1, email);
        	 	 statement.setString(2, pwd);
       	 		 ResultSet rs = statement.executeQuery();
        		 
        		 if( verify == false)
        		 {
        			 request.setAttribute("error", "Missed Captcha.");
        			 request.setAttribute("jsp", true);
        			 request.getRequestDispatcher("/jsp/portal.jsp").include(request, response);
        		 }
        		 else if (rs.next())
        		 {
        			 session.setAttribute("username", email);
        			 session.setAttribute("userId", rs.getInt("id"));
            		 rs.close();
            		 db.closeConnection();
        			 response.sendRedirect("/fabflix/main");
        		 }
        		 else
        		 {
        			 request.setAttribute("error", "Invalid e-mail and/or password.");
        			 request.setAttribute("jsp", true);
        			 request.getRequestDispatcher("/jsp/portal.jsp").include(request, response);
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
     			request.getRequestDispatcher("/jsp/error.jsp").include(request, response);
        	 }
    	 }
	}
}