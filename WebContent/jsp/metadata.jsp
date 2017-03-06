<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.ArrayList" %>

<%
	if (session.getAttribute("employee") == null || request.getAttribute("metadata") == null)
	{
		response.sendRedirect("../_dashboard");
		return;
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="./script/error.js"></script>
	<link rel="stylesheet" type="text/css" href="./style/error.css"/> 
	<link rel="stylesheet" type="text/css" href="./style/main.css"/> 
	<title>Cart</title>
</head>
<body>
	<div class="focus">
		<center><h1>Metadata</h1></center>
		<%
			out.println("<center><span><span> " + request.getAttribute("metadata") + "</span></span><br><br></center>");
		%>
		<!-- <center><button onclick="goBack()">Go Back To The Previous Page</button></center> -->
		<center></center><form action="./dashboardMain">
    		<input type="submit" value="Go back to previous page" />
		</form></center>
	</div>
</body>
</html>