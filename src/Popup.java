import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import general_helpers.DatabaseHelper;
import movie_list_helpers.MovieListDB;
import movie_list_helpers.PrintMovies;

/**
 * Servlet implementation class Popup
 */
@WebServlet("/Popup")
public class Popup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Popup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

			String movieId = request.getParameter("movieId");
			
			StringBuilder sb = new StringBuilder();
			DatabaseHelper db;
			try {
				db = new DatabaseHelper(false);
				
				Connection conn = db.getConnection();
	   	 		String query = "select * from movies where id = ?;";
	   	 		PreparedStatement statement = conn.prepareStatement(query);
	   	 		statement.setString(1, movieId);
	   	 		ResultSet rs = statement.executeQuery();
	   	 		rs.next();
				sb.append("<div><br><img src='" + rs.getString(5) + "' height='80' width='50'/><br><b>Year:</b> " + rs.getInt(3) + "<br><b>Director:</b> " + rs.getString(4));
				
				query = "select * from stars_in_movies sm inner join stars s on s.id = sm.star_id where sm.movie_id = ?;";
	   	 		statement = conn.prepareStatement(query);
	   	 		statement.setString(1, movieId);
	   	 		rs = statement.executeQuery();
				if (rs.next()) {
					sb.append("<br><b>Stars:</b> " + rs.getString(4) + " " + rs.getString(5));
				}
				while(rs.next()) {
					sb.append("<br>      " + rs.getString(4) + " " + rs.getString(5));
				}
				
				sb.append("<form action='./cart' method='post'><input type='hidden' name='movie_id' value='" + movieId + "'/>"
						+ "<input type='hidden' name='quantity' value='1'/>" +
				"<input class='submit' name='action' type='submit' value='Add to Cart'/></form></div>");
				response.setContentType("text/html");
			    response.setCharacterEncoding("UTF-8");
		        response.getWriter().write(sb.toString());
				
			} catch (SQLException e) {
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
