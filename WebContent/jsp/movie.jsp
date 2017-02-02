<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!-- Usage: ./movie?id=movie_id  (note to self: might want to switch to param usage for serv ) -->

<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="single_view_helpers.MovieViewDB" %>
<%@ page import="util.Movie" %>
<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="ecommerce_helpers.ShoppingCart" %>

<%
	if (session.getAttribute("username") == null || request.getParameter("id") == null)
	{
		response.sendRedirect("./login");
	}
	else if (request.getParameter("id") == null)
	{
		response.sendRedirect("./main");
	}

	ShoppingCart cart = new ShoppingCart(session);
	Movie movie = null;
	Integer movie_id = Integer.parseInt(request.getParameter("id"));
	int quantity = cart.getQuantity(movie_id);
	
	if (quantity == 0) // movie isnt in cart
	{
		try
		{
			MovieViewDB db = new MovieViewDB();
			movie = db.getMovie(movie_id);
			movie.setStars(db.getStarsForMovie(movie_id));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			response.getWriter().println("SQL Error: " + e.getMessage());
		}
	}
	else movie = cart.getMovie(movie_id); // cached, movie already in cart, performance optimization
%>

<!DOCTYPE html>
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
							<td class='info'><span><%=movie.getStarsHTMLString()%></span></td>
						<tr class='border_bottom'>
							<td class='field'>Price</td>
							<td class='info'><span>$14.99 per copy</span></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<p class='ad'><%= quantity == 0 ? "Want to purchase this item? Add it to your cart!" : "You already have this item in your cart. Want more? Update the quantity!" %></p>
		<form class='quantity' action='./cart' method='post'>
			<input type='number' name='number" "'min='1' value='<%= quantity == 0 ? 1 : quantity %>'/>
			<input class='submit' type='submit' value='<%= quantity == 0 ? "Add to Cart" : "Update Quantity" %>'/>
		</form>
		<p class ='ad'>You currently have <%= quantity %> order(s) of this movie in your cart.</p>
	</div>
</body>
</html>