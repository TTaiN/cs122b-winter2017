<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!-- Usage: ./movie?id=movie_id -->

<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="general_helpers.Movie" %>

<%
	if (session.getAttribute("username") == null || request.getAttribute("movie") == null)
	{
		response.sendRedirect("../login");
	}

	Movie movie = (Movie) request.getAttribute("movie");
	Integer quantity = movie.getQuantity();
	Integer movie_id = movie.getId();
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
				<!-- Source: http://stackoverflow.com/questions/3984287/how-to-show-alternate-image-if-source-image-is-not-found-onerror-working-in-ie -->
				<td class='container'><img class='banner' src='<%= movie.getBanner() %>' alt='(Picture of <%= movie.getTitle() %>)' onerror="this.onerror=null;this.src='./images/no-image.jpg'"/></td>
				<td class='container'>
					<table>
						<caption>Movie Information</caption>
						<tr class='border_bottom'>
							<td class='field'><span>Movie ID</span></td>
							<td class='info'><span><%= movie_id %></span><br></td>
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
							<td class='info'><span><%= movie.getTrailer().equals("(none)") ? "(none)" : "<a href='"+movie.getTrailer()+"'>Click here to watch the trailer</a>" %></span><br></td>
						</tr>
						<tr class='border_bottom'>
							<td class='field'>Genres</td>
							<td class='info'><span><%= movie.getGenresHTMLString() %></span></td>
						</tr>
						<tr class='border_bottom'>
							<td class='field'>Stars</td>
							<td class='info'><span><%= movie.getStarsHTMLString() %></span></td>
						</tr>
						<tr class='border_bottom/'>
							<td class='field'>Price</td>
							<td class='info'><span>$<%= movie.getPrice() %> per copy</span></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<p class='ad'><%= quantity == 0 ? "Want to purchase this item? Add it to your cart!" : "You already have this item in your cart. Want more? Update the quantity!" %></p>
		<form class='quantity' action='./cart' method='post'>
			<input type="hidden" name="movie_id" value='<%= movie_id %>'>
			<input type='number' name='quantity' min='<%= quantity == 0 ? 1 : 0 %>' value='<%= quantity == 0 ? 1 : quantity %>'/>
			<input class='submit' name='action' type='submit' value='<%= quantity == 0 ? "Add to Cart" : "Update Quantity" %>'/>
		</form>
		<p class ='ad'>You currently have <%= quantity %> order(s) of this movie in your cart.</p>
	</div>
</body>
</html>