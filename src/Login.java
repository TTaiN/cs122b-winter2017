import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import general_helpers.DatabaseHelper;

public class Login extends HttpServlet
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
            request.getRequestDispatcher("/jsp/login.jsp").include(request, response);
        }
        else 
        {
        	response.sendRedirect("./main");
        }
    }
    
     public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
     {
    	 response.setContentType("text/html");
    	 HttpSession session = request.getSession(); 
    	 try
    	 {
    		 DatabaseHelper db = new DatabaseHelper();

    		 String email = request.getParameter("email");
    		 String pwd = request.getParameter("pwd");

    		 ResultSet rs = db.executePreparedStatement("SELECT * from customers where email = '" + email + "'" + " and password = '" + pwd + "'");

    		 if (rs.next())
    		 {
    			 session.setAttribute("username", email);
    			 response.sendRedirect("./main");
    		 }
    		 else
    		 {
    			 request.setAttribute("error", "Invalid e-mail and/or password.");
    			 request.setAttribute("jsp", true);
    			 request.getRequestDispatcher("./jsp/login.jsp").include(request, response);
    		 }

    		 rs.close();
    		 db.closeConnection();
    	 }
    	 catch (Exception e)
    	 {
    		 e.printStackTrace();
    	 }
	}
}