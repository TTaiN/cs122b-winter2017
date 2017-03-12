import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.ResultSetMetaData;

import general_helpers.DatabaseHelper;

@WebServlet("/DashboardMain")
public class DashboardMain extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       

    public DashboardMain() 
    {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
		
		else if (request.getParameter("metadata") != null)
		{
			DatabaseHelper db;
			try 
			{
				db = new DatabaseHelper();
				Connection conn = db.getConnection();
				String query = "Select table_name from information_schema.tables where table_schema = 'moviedb'";
				PreparedStatement statement = conn.prepareStatement(query);
				ResultSet result = statement.executeQuery();
				
				StringBuilder sb = new StringBuilder();
				
				int j = 1;
	        	while (result.next())
	        	{
	            		String table = result.getString(1);            
	            		ResultSet result2 = db.executePreparedStatement("Select * from " + table); //ok?
	            
	            		sb.append("Table " + j++ + ": " + table + "<br>");
	            
	            		ResultSetMetaData metadata = (ResultSetMetaData) result2.getMetaData();
	            		sb.append("Attributes: <br>");
	            
	           		 for (int i = 1; i <= metadata.getColumnCount(); i++)
	                    sb.append( i + ". " + metadata.getColumnName(i) + " Type: " + metadata.getColumnTypeName(i) + "<br>");
	            		sb.append("<br>");
	        	}
	        	request.setAttribute("metadata", sb.toString());
	    		request.getRequestDispatcher("./jsp/metadata.jsp").include(request, response);		
	
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		} 
		else if (request.getParameter("insertStar") != null)
		{
			System.out.println("<span><span > Successfully inserted " + request.getAttribute("insertStar") + " row</span></span><br><br>");
		}
		else
		{
			request.getRequestDispatcher("./jsp/dashboard_main.jsp").include(request, response);		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
