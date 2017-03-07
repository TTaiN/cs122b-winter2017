

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import layout_helpers.TopMenu;

public class LiveSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LiveSearch() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("IN");
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if (username == null)
		{
			response.sendRedirect("./login");
			return;
		}

		String q = (String) request.getParameter("q");
		try {
			ArrayList<String> results = TopMenu.showResult(q);
			for( int i = 0; i < 10 && i < results.size(); i++)
			{
				PrintWriter out = response.getWriter();
			    out.println(results.get(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("OUT");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
