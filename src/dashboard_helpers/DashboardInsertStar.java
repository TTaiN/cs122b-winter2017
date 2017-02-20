package dashboard_helpers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import general_helpers.DatabaseHelper;

/**
 * Servlet implementation class DashboardInsertStar
 */

public class DashboardInsertStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardInsertStar() {
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
		String username = (String) session.getAttribute("employee");
		
		if (username == null)
		{
            response.sendRedirect("./_dashboard");
            return;
		}
		DatabaseHelper db;
		try {
			db = new DatabaseHelper();

			String query = "insert into stars values('" + request.getParameter("id") + "','" + request.getParameter("firstName") + "', '" + request.getParameter("lastName")+ "', '" + request.getParameter("dob") + "', '" + request.getParameter("photoUrl") + "')";
			query = query.replaceAll("''", "NULL");
			int row = db.executeInsertPS(query);
			System.out.println(query);	//debug
			
			request.setAttribute("notice", "Star inserted");
    		request.getRequestDispatcher("./jsp/dashboard_main.jsp").include(request, response);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			request.setAttribute("notice", e.getMessage());
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
