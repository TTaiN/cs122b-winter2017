<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.ArrayList" %>

<%
	if (session.getAttribute("username") == null || request.getAttribute("reason") == null || request.getAttribute("messages") == null)
	{
		response.sendRedirect("../login");
		return;
	}

	NumberFormat formatter = NumberFormat.getCurrencyInstance(); // Credit: http://stackoverflow.com/questions/13791409/java-format-double-value-as-dollar-amount
	String reason = (String) request.getAttribute("reason");
	ArrayList<String> messages = (ArrayList<String>) request.getAttribute("messages");
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
	<%
		TopMenu.print(response.getWriter());
	%>
	<div class="focus">
		<center><h1>Oops! (<%= reason %>)</h1></center>
		<%
			for (String message : messages)
			{
				out.println("<span>-><span style='color:red;'> " + message + "</span></span><br><br>");
			}
		%>
		<center><button onclick="goBack()">Go Back To The Previous Page</button></center>
	</div>
</body>
</html>