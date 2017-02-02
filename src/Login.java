import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Login extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    private static final String loginUser = "cs122b";
    private static final String loginPasswd = "cs122bgroup42";
    private static final String loginUrl = "jdbc:mysql://35.167.240.46/moviedb";

	public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT.";
    }

    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    { 
        response.setContentType("text/html");    // Response mime type
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        
        if (username == null)
        {
            request.getRequestDispatcher("/index.html").include(request, response);
        }
        else 
        {
        	response.sendRedirect("./main");
        	//response.getWriter().println("You're already logged in.");
        }
    }
    
     public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
    	 //if (request.getParameter(arg0))
    	 response.setContentType("text/html");    // Response mime type
    	 
    	 HttpSession session = request.getSession();
    	 
         // Output stream to STDOUT
         PrintWriter out = response.getWriter();

         try
            {
               Class.forName("com.mysql.jdbc.Driver").newInstance();

               Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
               // Declare our statement
               Statement statement = dbcon.createStatement();

 	          String email = request.getParameter("email");
 	          String pwd = request.getParameter("pwd");
 	        		  
               String query = "SELECT * from customers where email = '" + email + "'" + 
             		  " and password = '" + pwd + "'";

               // Perform the query
               ResultSet rs = statement.executeQuery(query);

               if (rs.next())
               {
                   //out.println("<HTML><HEAD><TITLE>Welcome Page</TITLE></HEAD>");
                   //String m_FN = rs.getString("first_name");
                   //String m_LN = rs.getString("last_name");
                   //out.println("<BODY>Welcome " + m_FN + " " + m_LN + "!");
                   session.setAttribute("username", email);
                  //out.println("CONGRATS!");
                   response.sendRedirect("./main");
               }
               else
               {
                   out.println("Invalid email or password.");
                   request.getRequestDispatcher("/index.html").include(request, response);
               }

               rs.close();
               statement.close();
               dbcon.close();
             }
         catch (SQLException ex) {
               while (ex != null) {
                     System.out.println ("SQL Exception:  " + ex.getMessage ());
                     ex = ex.getNextException ();
                 }  // end while
             }  // end catch SQLException

         catch(java.lang.Exception ex)
             {
             	ex.printStackTrace();
                 out.println("<HTML>" +
                             "<HEAD><TITLE>" +
                             "MovieDB: Error" +
                             "</TITLE></HEAD>\n<BODY>" +
                             "<P>SQL error in doGet: " +
                             ex.getMessage() + "</P></BODY></HTML>");
                 return;
             }
          out.close();
	} 
}