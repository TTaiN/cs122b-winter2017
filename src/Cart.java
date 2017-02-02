import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import util.Movie;


@WebServlet("/cart")

public class Cart extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public Cart() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			response.sendRedirect("./login");
		}
		
		LinkedHashMap<Movie, Integer> cart = (LinkedHashMap<Movie, Integer>) session.getAttribute("cart");
		if (cart == null)
		{
			session.setAttribute("cart", new LinkedHashMap<Movie, Integer>());
			//response.getWriter().println("[DEBUG] New cart made.");
			cart = (LinkedHashMap<Movie, Integer>) session.getAttribute("cart");
			cart.put(new Movie(1, "JSPs Are Awesome", 2017, "Thomas T Nguyen", "http://ia.imdb.com/media/imdb/01/I/95/71/38m.jpg", "http://ia.imdb.com/media/imdb/01/I/95/71/38m.jpg"), 1);
			cart.put(new Movie(2, "Donald Trump: The Dictator", 2015, "Akito T Nguyen", "http://ia.imdb.com/media/imdb/01/I/94/55/51m.jpg", "http://ia.imdb.com/media/imdb/01/I/94/55/51m.jpg"), 1);
			cart.put(new Movie(4, "United States Doomed Forever?!", 2016, "TTaiN T Nguyen", "http://ia.imdb.com/media/imdb/01/I/47/24/43m.jpg", "http://ia.imdb.com/media/imdb/01/I/47/24/43m.jpg"), 1);
			cart.put(new Movie(3, "Why I Hate Project Courses 101", 2014, "Pupcorn T Nguyen", "http://ia.imdb.com/media/imdb/01/I/34/82/91m.jpg", "http://ia.imdb.com/media/imdb/01/I/34/82/91m.jpg"), 1);
		}
		else
		{
			//response.getWriter().println("[DEBUG] Cart already exists.");
		}
		request.getRequestDispatcher("/cart.jsp").include(request, response);


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		doGet(request, response);
	}
}
