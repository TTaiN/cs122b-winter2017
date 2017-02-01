<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!-- Usage: ./movie?id=movieid  (note to self: might want to switch to param usage for serv ) -->

<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="util.Movie" %>
<%@ page import="util.TopMenu" %>
<%@ page import="util.DatabaseHelper" %>
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
	
	try
	{
		DatabaseHelper db = new DatabaseHelper();
		ResultSet rs = db.executeSQL("SELECT * FROM movies WHERE id = " + request.getParameter("id"));
		if (rs.next())
		{
			movie = new Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("year"), rs.getString("director"), rs.getString("banner_url"), rs.getString("trailer_url"));
			System.out.println("Created new movie!");
		}
		else throw new SQLException();
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
	<link rel="stylesheet" type="text/css" href="./style/cart.css"/> 
	<link rel="stylesheet" type="text/css" href="./style/main.css"/> 
	
	<title>Movie</title>
</head>
<body>
	<% TopMenu.print(response.getWriter()); %>
	<div class="focus">
		<h1>Movie Information</h1>
		<p ><%= movie.getTitle() %></p> <!--  Change to FirstName,LastName later  -->
		<table>
			<tr>
			    <th class='info'>Movie ID</th>
			    <th class='info'>Image</th>
			    <th class='info'>Title</th>
			    <th class='info'>Year</th>
			    <th class='info'>Director</th>
			    <th class='info'>Trailer</th>
			</tr>
			<tr>
				<td class='info'><span><%= movie.getId() %></span></td>
				<td class='info'><span><img class='banner' src='<%= movie.getBannerUrl() %>'/></span></td>
				<td class='info'><span><%= movie.getTitle() %></span></td>
				<td class='info'><span><%= movie.getYear() %></span></td>
				<td class='info'><span><%= movie.getDirector() %></span></td>
				<td class='info'><span><%= movie.getTrailerUrl() %></span></td>
			</tr>
		</table>
	</div>
</body>
</html>