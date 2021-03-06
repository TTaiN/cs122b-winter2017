package movie_list_helpers;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import general_helpers.Movie; //

public class PrintMovies {
	static private void printMovie(Movie m, PrintWriter out)
	{
		out.println("<div class='entry'>");
		out.println("<table class='movie_detail'>");
		out.println("<tr><td class='container'><a href='./movie?id=" + m.getId() + "'><img class='banner' src='" + m.getBanner() + 
				"' alt='(Picture of " + m.getTitle() + "' onerror=\"this.onerror=null;this.src='./images/no-image.jpg'\"" +
				"></a><br><br><div style='text-align:center'><form action='./cart' method='post'><input type='hidden' name='movie_id' value='" + m.getId() + "'/>"
						+ "<input type='hidden' name='quantity' value='1'/>" +
				"<input class='submit' name='action' type='submit' value='Add to Cart'/></form></div>"
				+ "</td><td class='container'>");
		out.println("<a href='./movie?id=" + m.getId() + "' class='popup' id='" + m.getId() + "' >");
		out.println(m.getTitle());
		out.println("</a></caption>");
		
		out.println("<tr class='border_bottom'><td class='field'><span>Movie ID</span></td>");
		out.println("<td class='info'><span>" + m.getId() + "</span><br></td></tr>");
		
		out.println("<tr class='border_bottom'><td class='field'><span>Year</span></td>");
		out.println("<td class='info'><span>" + m.getYear() + "</span><br></td></tr>");
		
		out.println("<tr class='border_bottom'><td class='field'><span>Director</span></td>");
		out.println("<td class='info'><span>" + m.getDirector() + "</span><br></td></tr>");
		
		out.println("<tr class='border_bottom'><td class='field'><span>Trailer</span></td>");
		if(m.getTrailer().equals("(none)"))
			out.println("<td class='info'><span>" + m.getTrailer() + "</span><br></td></tr>");
		else
			out.println("<td class='info'><span> <a href='" + m.getTrailer() + "'>Click here to watch the trailer</a>" 
					+ "</span><br></td></tr>");
		
		LinkedHashMap<Integer, String> genres = m.getGenres();
		out.println("<tr class='border_bottom'><td class='field'><span>Genres</span></td>");
		if( genres.isEmpty() )
		{
			out.println("<td class='info'><span>(none)</span></br></td></tr>");
		}
		else
		{
			out.println("<td class='info'><span>");
			for (Map.Entry<Integer, String> genre : genres.entrySet()) 
				out.println("<a href='./movielist?genre=" + genre.getValue() + "'>" + genre.getValue() + "</a><br>");
			out.println("</span></td></tr>");
		}
		
		LinkedHashMap<Integer, String> stars = m.getStars();
		out.println("<tr class='border_bottom'><td class='field'><span>Stars</span></td>");
		if( stars.isEmpty() )
		{
			out.println("<td class='info'><span>(none)</span></td></table></td></tr></table>");
		}
		else
		{
			out.println("<td class='info'><span>");
			for (Map.Entry<Integer, String> star : stars.entrySet()) 
				out.println("<a href='./star?id=" + star.getKey() + "'>" + star.getValue() + "</a><br>");
			out.println("</span></td></table></td></tr></table>");
		}
		out.println("</div>");		
	}
	
	public static void printSort(PrintWriter out, String genre, String firstChar, String title, String year, String director, String star, String limit, String sort)
	{
        out.println("<a href=\"./movielist?genre="+genre+"&firstchar="+firstChar+
        		"&title="+title+"&year="+year+"&director="+director+"&star="+star+"&sort="+sort+"&limit="+limit+"\" class=button>"+sort +"</a>");
	}
	public static void printMovies(PrintWriter out, List<Movie> movieList, int page, int limit, String query)
	{
		if(movieList.isEmpty())
		{
			out.println("No results found.");
		}
		else if(movieList.size() < limit+1)
		{
			for( int i = 0; i < movieList.size(); i++ )
			{
				out.println("<div class= 'centered'>");
				printMovie(movieList.get(i), out);
				out.println("</div>");
			}
			if( page > 1)
				out.println("<div class='page-div' align=center><td><a href=\"./movielist?" + query + "&page=" + (page-1) 
						+ "&limit=" + limit + "\">" + "Prev</a> Page " + page + "<br></td></div>");
			else
				out.println("<div class='page-div' align=center><td>Page " + page + "<br></td></div>");
		}
		else
		{
			for( int i = 0; i < movieList.size()-1; i++ )
			{
				out.println("<div class= 'centered'>");
				printMovie(movieList.get(i), out);
				out.println("</div>");
			}
			if( page > 1)
			{
				out.println("<div class='page-div' align=center><td><a href=\"./movielist?" + query + "&page=" + (page-1) 
						+ "&limit=" + limit + "\">" + "Prev</a> Page " + page + " ");
				out.println("<a href=\"./movielist?" + query + "&page=" + (page+1) + "&limit=" + limit + "\">" 
		    		 + "Next</a><br></td></div>");
			}
			else
			{
				out.println("<div class='page-div' align=center>Page " + page +" <a href=\"./movielist?" + query + "&page=" 
						+ (page+1) + "&limit=" + limit + "\">" + "Next</a><br></td></div>");
				
			}
		}
	}
	
	public static void printDropDownMenu(PrintWriter out, String genre, String firstChar, String title, String year, String director, String star, String sort)
	{
		out.println("<div class=\"dropdown\"> <button class=\"dropbtn\">Results Per Page</button>"
				+ "<div class=\"dropdown-content\">");
		int[] n = {10, 25, 50, 100};
		for(int i : n)
		{
			out.println("<a href=\"./movielist?genre="+genre+"&firstchar="+firstChar+
        		"&title="+title+"&year="+year+"&director="+director+"&star="+star+"&sort="+sort+"&limit="+i+"\">"+i+"</a>");
		}
		out.println("</div></div>");
	}

}