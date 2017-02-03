package movie_list_helpers;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import general_helpers.Movie;

public class PrintMovies {
	static private void printMovie(Movie m, PrintWriter out)
	{
		out.println("<table class='movie_detail'>");
		out.println("<tr> <td class='container'><img src=" + m.getBanner() + "/></td><td class='container'>");
		out.println("<table><caption><a href='./movie?id=" + m.getId() + "'>" + m.getTitle() + "</a></caption>");
		
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
		
		LinkedHashMap<Integer, String> stars = m.getStars();
		out.println("<tr class='border_bottom'><td class='field'><span>Stars</span></td>");
		if( stars.isEmpty() )
		{
			out.println("<td class='info'><span>(none)</span></td></td></tr>");
		}
		else
		{
			out.println("<td class='info'><span>");
			for (Map.Entry<Integer, String> star : stars.entrySet()) 
				out.println("<a href='./star?id=" + star.getKey() + "'>" + star.getValue() + "</a><br>");
			out.println("</span></td></td></tr>");
		}
		
		List<String> genres = m.getGenres();
		out.println("<tr class='border_bottom'><td class='field'><span>Genres</span></td>");
		if( genres.isEmpty() )
		{
			out.println("<td class='info'><span>(none)</span></td></table></td></tr>");
		}
		else
		{
			out.println("<td class='info'><span>");
			for (String g : genres) 
				out.println(g + "</a><br>");
			out.println("</span></td></table></td></tr>");
		}
		
	}
	public static void printSort(PrintWriter out, String genre, String firstChar, String title, String year, String director, String star, String sort)
	{
        out.println("<a href=\"./movielist?genre="+genre+"&firstChar="+firstChar+
        		"&title="+title+"&year="+year+"&director="+director+"&star="+star+"&sort="+sort+"\" class=button>"+sort +"</a>");
	}
	public static void printMovies(PrintWriter out, List<Movie> movieList, int page, int limit, String query)
	{
		if(movieList.isEmpty() && page == 1)
		{
			out.println("No results found.");
		}
		else if(movieList.isEmpty() && page > 1)
		{
			out.println("No more results");
			//out.println("<a href=\"./movielist?genre=" + genre + "&page=" + (page-1) + "&sort=" + sort + "\">" 
			//+ "Go back</a> a page<br>");
			
			out.println("<a href=\"./movielist?" + query + "&page=" + (page-1) + "\">" 
					+ "Go back</a> to previous page<br>");
		}
		else if(movieList.size() < limit)
		{
			for( int i = 0; i < movieList.size(); i++ )
			{
				printMovie(movieList.get(i), out);
			}
			if( page > 1)
				out.println("<td><a href=\"./movielist?" + query + "&page=" + (page-1) + "\">" 
		        		+ "Prev</a><br></td>");
		}
		else
		{
			for( int i = 0; i < movieList.size(); i++ )
			{
				printMovie(movieList.get(i), out);
			}
			if( page > 1)
			{
				out.println("<div align=center><td><a href=\"./movielist?" + query + "&page=" + (page-1) + "\">" 
		        		+ "Prev</a> Page " + page + " ");
				out.println("<a href=\"./movielist?" + query + "&page=" + (page+1) + "\">" 
		    		 + "Next</a><br></td></div>");
			}
			else
			{
				out.println("<div align=center>Page " + page +" <a href=\"./movielist?" + query + "&page=" + (page+1) + "\">" 
			    		 + "Next</a><br></td></div>");
				
			}
		}
	}

}