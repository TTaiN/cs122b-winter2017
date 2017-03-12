<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<%@ page import="general_helpers.DatabaseHelper" %>

<%
	if (session.getAttribute("username") == null || request.getAttribute("movie") == null)
	{
		response.sendRedirect("../login");
	}
%>

<!DOCTYPE html>
<html>
<head>
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script type="text/javascript">
		function showResult(str) {
	
			$.get("./livesearch", {q:str}, function(data){
			$("#results").html(data);
			});
		}
	</script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="./style/browse.css"/>
	<link rel="stylesheet" type="text/css" href="./style/livesearch.css"/> 
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
	ArrayList<String> initials = (ArrayList<String>) request.getAttribute("initials");
	/* new ArrayList<String>(Arrays.asList(s.split(","))); */
	for (String initial : initials)
	{
		out.println("<a href=\"./movielist?firstchar=" + initial + "\">" + initial + "</a>");
	}
	
	%>
	</p>
</div>

<br><H2>Browse by genre</H2>

	<%
	ArrayList<String> genres = (ArrayList<String>) request.getAttribute("genres");
	int rowLimit = (genres.size() + 3) / 4;	//ceiling div w/o importing math
	System.out.println(rowLimit);
	
	int i = 0;
	out.println("<div class='wrapper'>");

	for (int j = 0; j<genres.size(); j++)
	{
		if (i==0) {
			out.println("<div id='cols'>");
			out.println("<ul style='list-style: none;'>");
			out.println("<li><a href='./movielist?genre=" + genres.get(j) + "'>" + genres.get(j) + "</a></li><br>");
			i++;
		}
		else if (i<rowLimit-1) { 
			out.println("<li><a href='./movielist?genre=" + genres.get(j) + "'>" + genres.get(j) + "</a></li><br>");
			i++;
		} else {
			out.println("<li><a href='./movielist?genre=" + genres.get(j) + "'>" + genres.get(j) + "</a></li><br>");
			out.println("</ul></div>");
			i = 0;
		}
	}
	out.println("</div>");
	 
	%>

</BODY>
</html>