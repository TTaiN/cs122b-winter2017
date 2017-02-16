<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	if (session.getAttribute("username") != null || request.getAttribute("jsp") == null)
	{
		response.sendRedirect("../main");
		return;
	}
%>

<!DOCTYPE html>
<html>
<head>
	<title>Fabflix Login</title>
  	<link rel="stylesheet" type="text/css" href="./style/main.css"/>
	<script src='https://www.google.com/recaptcha/api.js'></script>
</head>

<body>
	<br>
	<center><img src="./images/logo.png" alt="logo"></center>
	<br>
	<h1 align="center">Login Page</h1>
	<%= request.getAttribute("error") == null ? "" : "<center><p style='color:red;'>" + request.getAttribute("error") + "</p></center>" %>
	<div align="center">
		<form action="./login" method="POST">
			E-mail: <input type="text" name="email" ><br>
			Password: <input type="password" name="pwd" ><br>
			<input type="submit" value="Login">
			<div class="g-recaptcha" data-sitekey="6LelyBUUAAAAAFASK8XOU_G4y3Ke9SQAmF7dX0QC"></div>
		</form>
	</div>
</body>
</html>