

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.ResultSetMetaData;

import general_helpers.DatabaseHelper;

/**
 * Servlet implementation class DashboardMain
 */
@WebServlet("/DashboardMain")
public class DashboardMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardMain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("employee");
		if (username == null)
		{
            response.sendRedirect("./_dashboard");
            return;
		}
		
		else if (request.getParameter("metadata") != null)
		{
			// get the metadata somehow
			DatabaseHelper db;
			try {
				db = new DatabaseHelper();

				String query = "Select table_name from information_schema.tables where table_schema = 'moviedb'";
				ResultSet result = db.executeSQL(query);
				
				StringBuilder sb = new StringBuilder();
				
				int j = 1;
	        	while (result.next())
	        	{
	            		String table = result.getString(1);            
	            		ResultSet result2 = db.executeSQL("Select * from " + table);
	            
	            		sb.append("Table " + j++ + ": " + table + "<br>");
	            
	            		ResultSetMetaData metadata = (ResultSetMetaData) result2.getMetaData();
	            		sb.append("Attributes: <br>");
	            
	           		 for (int i = 1; i <= metadata.getColumnCount(); i++)
	                    sb.append( i + ". " + metadata.getColumnName(i) + " Type: " + metadata.getColumnTypeName(i) + "<br>");
	            		sb.append("<br>");
	        	}
	        	request.setAttribute("metadata", sb.toString());
	    		request.getRequestDispatcher("./jsp/metadata.jsp").include(request, response);		
	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
