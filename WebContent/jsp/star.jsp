<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!-- Usage: ./star?id=star_id  -->

<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="general_helpers.Star" %>

<%
	if (session.getAttribute("username") == null || request.getAttribute("star") == null)
	{
		response.sendRedirect("./login");
	}
	Star star = (Star) request.getAttribute("star");
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="./style/star.css"/> 
	<link rel="stylesheet" type="text/css" href="./style/main.css"/> 
	
	<title>Movie</title>
</head>
<body>
	<% TopMenu.print(response.getWriter()); %>
	<div class="focus">
		<h1 class='title'><%= star.getFirstName() + " " + star.getLastName() %></h1>
		<table class='movie_detail'>
			<tr>
				<td class='container'><img src='<%= star.getPhoto() %>'/></td>
				<td class='container'>
					<table>
						<caption>Star Information</caption>
						<tr class='border_bottom'>
							<td class='field'><span>Star ID</span></td>
							<td class='info'><span><%= star.getId() %></span><br></td>
						</tr>
						<tr class='border_bottom'>
							<td class='field'><span>Name</span></td>
							<td class='info'><span><%= star.getFirstName() + " " + star.getLastName() %></span><br></td>
						</tr>
						<tr class='border_bottom'>
							<td class='field'>Date of Birth</td>
							<td class='info'><span><%= star.getDOB() %></span><br></td>
						</tr>
						<tr class='border_bottom'>
							<td class='field'>Movies</td>
							<td class='info'><span><%= star.getMoviesHTMLString() %></span></td>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>