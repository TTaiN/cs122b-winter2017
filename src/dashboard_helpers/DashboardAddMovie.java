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
 * Servlet implementation class DashboardAddMovie
 */
public class DashboardAddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardAddMovie() {
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

			String query = "call add_movie ('" + request.getParameter("movieId") + "','" + request.getParameter("title") + "','" +request.getParameter("year") + "','" + request.getParameter("director") + "','" + request.getParameter("bannerUrl") + "','" + request.getParameter("trailerUrl") + "','" + request.getParameter("starId") + "','" + request.getParameter("firstName") + "', '" + request.getParameter("lastName")+ "', '" + request.getParameter("dob") + "', '" + request.getParameter("photoUrl") + "','" + request.getParameter("genreId") + "','" + request.getParameter("genreName")+ "');";
			query = query.replaceAll("''", "NULL");
			System.out.println(query);	//debug
			ResultSet rs = db.executeSQL(query); 
			
			request.setAttribute("notice", "Movie inserted");
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
