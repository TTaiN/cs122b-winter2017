package dasboard_helpers;

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
		String username = (String) session.getAttribute("username");
		
		if (username == null)
		{
            response.sendRedirect("./login");
            return;
		}
		DatabaseHelper db;
		try {
			db = new DatabaseHelper();

			String query = "insert into stars values('" + request.getAttribute("id") + "','" + request.getAttribute("firstName") + "', '" + request.getAttribute("lastName")+ "', '" + request.getAttribute("dob") + "', '" + request.getAttribute("photoUrl") + "')";
			ResultSet result = db.executeSQL(query);
			System.out.println(result.toString());
			request.setAttribute("insertStar", result.toString());
    		request.getRequestDispatcher("./jsp/dashboard.jsp").include(request, response);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
