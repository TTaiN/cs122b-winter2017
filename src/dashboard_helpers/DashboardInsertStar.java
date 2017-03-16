package dashboard_helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import general_helpers.DatabaseHelper;

public class DashboardInsertStar extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public DashboardInsertStar() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        response.setContentType("text/html");
		HttpSession session = request.getSession();
        String employee = (String) session.getAttribute("employee");
        String username = (String) session.getAttribute("username");
        
		if (employee == null && username == null)
		{
            response.sendRedirect("./_dashboard");
            return;
		}
		else if (employee == null && username != null)
		{
            response.sendRedirect("./main");
            return;
		}
		
		DatabaseHelper db;
		
		try 
		{
			db = new DatabaseHelper(true);
			Connection conn = db.getConnection();
			String id = request.getParameter("id");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String dob = request.getParameter("dob");
			String photoUrl = request.getParameter("photoUrl");
			
			String query = "insert into stars values(?, ?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(query);
			
			if (id.isEmpty()) {
				statement.setNull(1, java.sql.Types.INTEGER);
			} else {
				statement.setInt(1, Integer.parseInt(id));
			}
   	 		statement.setString(2, firstName);
   	 		statement.setString(3, lastName);
   	 		statement.setString(4, dob);
   	 		statement.setString(5, photoUrl);
   	 		int row = statement.executeUpdate();	//null empty string problem?
			
			request.setAttribute("notice", "Star inserted");
    		request.getRequestDispatcher("./jsp/dashboard_main.jsp").include(request, response);	
    		
    		db.closeConnection();
		} 
		catch (SQLException e) 
		{
			request.setAttribute("notice", e.getMessage());
    		request.getRequestDispatcher("./jsp/dashboard_main.jsp").include(request, response);	
    		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
}
