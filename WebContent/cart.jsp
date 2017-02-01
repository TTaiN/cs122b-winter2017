<%@ page import="java.util.HashMap" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
	if (session.getAttribute("username") == null)
	{
		response.sendRedirect("./login");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="./style/cart.css"/> 
<title>Cart</title>
</head>
<body>
<h1>Fabflix Cart</h1>
<p>Welcome to your cart, ${sessionScope.username}!</p> <!--  Change to FirstName,LastName later  -->
<table>
	<tr>
	    <th>Movie ID</th>
	    <th>Title (Year)</th>
	    <th>Director</th>
	    <th>Image</th>
	    <th>Quantity</th>
	    <th>Subtotal</th>
	</tr>
	<%
		if (session.getAttribute("cart") == null)
		{
			out.println("You currently have nothing in your cart. Purchase something!");
		}
		else
		{
			HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
		}
	%>
</table>

</body>
</html>