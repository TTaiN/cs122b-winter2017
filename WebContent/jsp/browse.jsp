<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>

<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<%@ page import="general_helpers.DatabaseHelper" %>

<%
	if (session.getAttribute("username") == null || request.getAttribute("movie") == null)
	{
		response.sendRedirect("./login");
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./style/browse.css"/>
	<title>Browse</title>
</head>
<BODY>
	<%
	if ((request.getAttribute("inMain") == null))
	{
		TopMenu.print(response.getWriter());
	}
	%>
<div class="wrapper">
	
	<H2>Browse by title</H2>

	<p ALIGN="CENTER">
				    
			   
	<%
	DatabaseHelper db = new DatabaseHelper();
	String query = "SELECT left(title,1) from movies group by left(title,1) order by title";
	ResultSet rs = db.executeSQL(query);
	while (rs.next()) 
	{
		out.println("<a href=\"./movielist?firstchar=" + rs.getString(1) + "\">" + rs.getString(1) + "</a>");
	}%>
	</p>
</div>

<br><H2>Browse by genre</H2>
<div class="wrapper">
	<div id="cols">
		<ul style="list-style: none;"> 
			<li><a href="./movielist?genre=Action">Action</a></li>
			<li><a href="./movielist?genre=Adv nture">Adventure</a></li>
			<li><a href="./movielist?genre=Animation">Animation</a></li>
			<li><a href="./movielist?genre=Arts">Arts</a></li>
			<li><a href="./movielist?genre=Classic">Classic</a></li>
			<li><a href="./movielist?genre=Com edy">Comedy</a></li>
			<li><a href="./movielist?genre=Crime">Crime</a></li>
		</ul>
	</div>
	<div id="cols">
		<ul style="list-style: none;">		    		

			<li><a href="./movielist?genre=Documentary">Documentary</a></li>
			<li><a href="./movielist?genre=Drama">Drama</a></li>
			<li><a href="./movielist?genre=Epics">Epics/Historical</a></li>
			<li><a href="./movielist?genre=Family">Family</a></li>
			<li><a href="./movielist?genre=Fantasy">Fantasy</a></li>
			<li><a href="./movielist?genre=Foreign">Foreign</a></li>
			<li><a href="./movielist?genre=Gangster">Gangster</a></li>
			
		</ul>
	</div>
	<div id="cols">
		<ul style="list-style: none;">	
			
			<li><a href="./movielist?genre=Horror">Horror</a></li>
			<li><a href="./movielist?genre=Indie">Indie</a></li>
			<li><a href="./movielist?genre=Bond">James Bond</a></li>
			<li><a href="./movielist?genre=Musical">Musical</a></li>
			<li><a href="./movielist?genre=Mystery">Mystery</a></li>
			<li><a href="./movielist?genre=Roman">Romance</a></li>
			<li><a href="./movielist?genre=Sci">Science Fiction</a></li> 

		</ul>
	</div>
	<div id="cols">
		<ul style="list-style: none;">		    		

			<li><a href="./movielist?genre=Spy">Spy</a></li>
			<li><a href="./movielist?genre=Suspense">Suspense</a></li>
			<li><a href="./movielist?genre=Thriller">Thriller</a></li>
			<li><a href="./movielist?genre=War">War</a></li>
			
		</ul>
	</div>
</div>
</BODY>
</html>