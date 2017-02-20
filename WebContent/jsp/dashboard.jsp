<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	if (session.getAttribute("employee") != null || request.getAttribute("jsp") == null)
	{
		response.sendRedirect("../_dashboard");
		return;
	}
%>

<!DOCTYPE html>
<html>
<head>
	<title>Dashboard Login</title>
  	<link rel="stylesheet" type="text/css" href="./style/main.css"/>
	<!--<script src='https://www.google.com/recaptcha/api.js'></script>-->
</head>

<body>
	<br>
	<center><img src="./images/logo.png" alt="logo"></center>
	<br>
	<h1 align="center">Dashboard Login</h1>
	<%= request.getAttribute("error") == null ? "" : "<center><p style='color:red;'>" + request.getAttribute("error") + "</p></center>" %>
	<div align="center">
		<form action="./_dashboard" method="POST">
			E-mail: <input type="text" name="email" ><br>
			Password: <input type="password" name="pwd" ><br>
			<input type="submit" value="Login"><br><br>
		</form>
	</div>
</body>
</html>