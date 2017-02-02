<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="util.Movie" %>
<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="ecommerce_helpers.ShoppingCart" %>

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
	<link rel="stylesheet" type="text/css" href="./style/main.css"/> 
	
	<title>Cart</title>
</head>
<body>
	<%
		TopMenu.print(response.getWriter());
	%>

	<div class="focus">
		<h1>Cart</h1>
		<p >Welcome to your cart, ${sessionScope.username}!</p> <!--  Change to FirstName,LastName later  -->
		<table>
			<tr>
			    <th class='info'>Movie ID</th>
			    <th class='info'>Image</th>
			    <th class='info'>Title (Year)</th>
			    <th class='info'>Price</th>
			    <th class='info'>Quantity</th>
			    <th class='info'>Subtotal</th>
			</tr>
			<%
				ShoppingCart cart = new ShoppingCart(session);
		
				if (!cart.exists() || cart.isEmpty())
				{
					out.println("<td><span>You currently have nothing in your cart. Purchase something!</span></td>");
				}
				else
				{
					for (Integer id : cart.getMovies())
					{
						Movie current = cart.getMovie(id);
						out.println("<tr>");
						out.println("<td class='info'><span>" + current.getId() + "</span></td>");
						out.println("<td class='info'><span><img class='banner' src='" + current.getBanner() + "'/></span></td>");
						out.println("<td class='info'><span><a href='./movie?id=" + current.getId() + "'>" + current.getTitle() + "</a> (" + current.getYear() + ")" + "</span></td>");
						out.println("<td class='info'><span>$" + current.getPrice() + "</span></td>");
						out.println("<td class='info'>");
						out.println("<span>" + current.getQuantity() + " order(s)</span><br><br>");
						out.println("<form class='quantity' action='./cart' method='post'>");
						out.println("<input type='number' name='quantity" + current.getId() + "'min='0' value='" + current.getQuantity() + "'/>");
						out.println("<input class='submit' type='submit' value='Update Quantity'/>");
						out.println("</form>");
						out.println("</td>");
						out.println("<td class='info'>");
						out.println("$" +  current.getQuantity() * current.getPrice());
						out.println("</td>");
					}
				}
			%>
		</table>
	</div>
</body>
</html>