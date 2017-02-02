<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!-- Usage: ./movie?id=movieid  (note to self: might want to switch to param usage for serv ) -->

<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="util.Movie" %>
<%@ page import="util.TopMenu" %>
<%@ page import="single_view_helpers.MovieViewDB" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.ResultSet" %>

<%
	if (session.getAttribute("username") == null || request.getParameter("id") == null)
	{
		response.sendRedirect("./login");
	}
	else if (request.getParameter("id") == null)
	{
		response.sendRedirect("./main");
	}

	Movie movie = null;
	Integer movie_id = Integer.parseInt(request.getParameter("id"));
	
	try
	{
		MovieViewDB db = new MovieViewDB();
		movie = db.getMovie(movie_id);
		movie.setStars(db.getStarsForMovie(movie_id));
	}
	catch (SQLException e)
	{
		e.printStackTrace();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="./style/movie.css"/> 
	<link rel="stylesheet" type="text/css" href="./style/main.css"/> 
	
	<title>Movie</title>
</head>
<body>
	<% TopMenu.print(response.getWriter()); %>
	<div class="focus">
		<h1 class='title'><%= movie.getTitle() %></h1>
		<table class='movie_detail'>
		<tr>
			<td class='container'><img src='<%= movie.getBanner() %>'/></td>
			<td class='container'>
				<table>
					<caption>Movie Information</caption>
					<tr class='border_bottom'>
						<td class='field'><span>Movie ID</span></td>
						<td class='info'><span><%= movie.getId() %></span><br></td>
					</tr>
					<tr class='border_bottom'>
						<td class='field'><span>Movie Title</span></td>
						<td class='info'><span><%= movie.getTitle() %></span><br></td>
					</tr>
					<tr class='border_bottom'>
						<td class='field'>Year</td>
						<td class='info'><span><%= movie.getYear() %></span><br></td>
					</tr>
					<tr class='border_bottom'>
						<td class='field'>Director</td>
						<td class='info'><span><%= movie.getDirector() %></span><br></td>
					</tr>
					<tr class='border_bottom'>
						<td class='field'>Trailer</td>
						<td class='info'><span><a href='<%= movie.getTrailer() %>'>Click here to watch the trailer</a></span><br></td>
					</tr>
					<tr class='border_bottom'>
						<td class='field'>Stars</td>
						<td class='info'>
							<%
								for (Integer star_id : movie.getStars().keySet())
								{
									response.getWriter().print("<a href='./star.jsp?id=" + star_id + "'>" + movie.getStars().get(star_id) + "</a>, ");
								}
							%>
						</td>
					<tr class='border_bottom'>
						<td class='field'>Price</td>
						<td class='info'><span>$14.99 per copy</span></td>
					</tr>
				</table>
			</td>
		</tr>
		
		</table>

	</div>
</body>
</html>