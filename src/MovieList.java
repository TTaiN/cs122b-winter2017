

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import general_helpers.Movie;
import layout_helpers.TopMenu;
import movie_list_helpers.MovieListDB;
import movie_list_helpers.PrintMovies;

/**
 * Servlet implementation class MovieList
 */
@WebServlet("/MovieList")
public class MovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MovieList() {
        super();
    }

    private String nullToBlank(String str)
    {
    	if(str == null)
    		return "";
    	else
    		return str;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if (username == null)
		{
			request.getRequestDispatcher("./login").include(request, response);
		}
		else
		{
	        response.setContentType("text/html");  
	        PrintWriter out=response.getWriter();
			out.println("<html><head><link rel='stylesheet' type='text/css' href='./style/main.css'/>\n"
					+ "<link rel='stylesheet' type='text/css' href='./style/movieList.css'/><title>Movie List</title></head>");
			TopMenu.print(out);
		    out.println("<BODY>");
		    out.println("<H1 ALIGN=\"CENTER\">Movie List</H1>\n\n");
			List<Movie> movieList = new ArrayList<Movie>();
	        String pageString = request.getParameter("page");
	        int page;
	        int limit;
	        if( pageString == null )
				page = 1;
	        else
	        	page = Integer.parseInt(pageString);
	        
	        // Get all possible parameters
	        String genre = request.getParameter("genre");
	        String firstChar = request.getParameter("firstchar");
	        String title = request.getParameter("title");
	        String year = request.getParameter("year");
	        String director = request.getParameter("director");
	        String star = request.getParameter("star");
	        String sort = request.getParameter("sort");
	        String lim = request.getParameter("limit");
	        if(lim == null || lim == "")
	        	limit = 10;
	        else
	        	limit = Integer.parseInt(lim);
	        
	        out.println("<div align=center>Sort by: ");
	        PrintMovies.printSort(out, nullToBlank(genre), nullToBlank(firstChar), nullToBlank(title),
	        		nullToBlank(year), nullToBlank(director), nullToBlank(star), nullToBlank(lim), "Title ASC");
	        PrintMovies.printSort(out, nullToBlank(genre), nullToBlank(firstChar), nullToBlank(title),
	        		nullToBlank(year), nullToBlank(director), nullToBlank(star), nullToBlank(lim), "Title DESC");
	        PrintMovies.printSort(out, nullToBlank(genre), nullToBlank(firstChar), nullToBlank(title),
	        		nullToBlank(year), nullToBlank(director), nullToBlank(star), nullToBlank(lim), "Year ASC");
	        PrintMovies.printSort(out, nullToBlank(genre), nullToBlank(firstChar), nullToBlank(title),
	        		nullToBlank(year), nullToBlank(director), nullToBlank(star), nullToBlank(lim), "Year DESC");
	        out.println("</div>");
	        
	        out.println("<div align=center>");
	        PrintMovies.printDropDownMenu(out, nullToBlank(genre), nullToBlank(firstChar), nullToBlank(title),
	        		nullToBlank(year), nullToBlank(director), nullToBlank(star), nullToBlank(sort));
	        out.println("<div>");


	        
	        // If not specified sort by title by default
	        if(sort == null || sort == "")
	        {
	        	sort = "title";
	        }
	        
	        // Check if browse by genre
	        if(genre != null && genre != "")
	        {
	        	String query = "genre=" + genre + "&sort=" + sort;
	        	movieList = MovieListDB.getMovies(limit, (page-1)*limit, genre, sort);
	        	PrintMovies.printMovies(out, movieList, page, limit, query);
	        }
	        
	        // Check if browse by title
	        else if(firstChar != null && firstChar != "")
	        {
	        	String query = "firstchar=" + firstChar + "&sort=" + sort;
	        	movieList = MovieListDB.getMovies(limit, (page-1)*limit, firstChar.charAt(0), sort);
	        	PrintMovies.printMovies(out, movieList, page, limit, query);
	        }
	        
	        // Else assume search
	        else
	        {
	        	title = nullToBlank(title);
	        	year = nullToBlank(year);
	        	director = nullToBlank(director);
	        	star = nullToBlank(star);

	        	String query = "title=" + title + "&year=" + year + "&director=" + director +
	        			"&star=" + star + "&sort=" + sort;
	        	
	        	movieList = MovieListDB.getMovies(limit, (page-1)*limit, title, director, year, star, sort);
	        	PrintMovies.printMovies(out, movieList, page, limit, query);
	        }

		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
