<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<title>Fabflix Login</title>
	<script src='https://www.google.com/recaptcha/api.js'></script>
	<style>
		.topmenu
		{
		     margin: 0 auto;
		     width: 350px; 
		}
		
		.topmenu_table
		{
		   border: 1px solid black;
		   text-align:center;
		   width: 350px;
		}
		
		.topmenu_button
		 {
		   border: 1px black;
		   border-right: 1px black;
		   text-align:center;
		   padding:3px;
		 }
		 
		 .topmenu_caption
		 {
		     font-size:110%;
		 }
		
		body 
		{
		    background-color: rgb(253,245,230);
			background-size: cover;
			background-repeat: no-repeat;
		}
	</style>
</head>

<body>
	<br>
	<center><img src="http://ttain.tk/logo.png" alt="logo"></center>
	<br>
	<h1 align="center">Login Page</h1>
	<%= request.getAttribute("error") == null ? "" : "<center><p style='color:red;'>" + request.getAttribute("error") + "</p></center>" %>
	<div align="center">
		<form method="POST">
			E-mail: <input type="text" name="email" ><br>
			Password: <input type="password" name="pwd" ><br>
			<input type="submit" value="Login"><br><br>
			<div class="g-recaptcha" data-sitekey="6LdaxxUUAAAAAC9gQgGvJoqT1bAUTcDviA4zCB_t"></div>
		</form>
		<br>
		<a href="/fabflix/_dashboard">Are you an employee? Click here to login</a>
	</div>
</body>
</html>