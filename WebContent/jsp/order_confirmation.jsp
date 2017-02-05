<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="general_helpers.Movie" %>
<%@ page import="ecommerce_helpers.ShoppingCart" %>
<%@ page import="java.text.NumberFormat" %>

<%
	ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
	if (session.getAttribute("username") == null || request.getAttribute("jsp") == null || cart == null || cart.isEmpty())
	{
		response.sendRedirect("../login");
		return;
	}

	NumberFormat formatter = NumberFormat.getCurrencyInstance(); // Credit: http://stackoverflow.com/questions/13791409/java-format-double-value-as-dollar-amount
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="./style/checkout.css"/> 
	<link rel="stylesheet" type="text/css" href="./style/main.css"/> 
	<title>Order Confirmation</title>
</head>
<body>
	<%
		TopMenu.print(response.getWriter());
	%>

	<div class="focus">
		<h1>Order Confirmation</h1>
		<p style="color: green;">[NOTICE] ${sessionScope.username}, your order was successful!</p> <!--  Change to FirstName,LastName later  -->
		<center><h3>Customer Information Summary</h3></center>
		<table align="center">
			<tr class='border_bottom'>
				<td class='field'><span>Credit Card Number</span></td>
				<td class='info'><span><%= cart.getNumber() %></span><br></td>
			</tr>
			<tr class='border_bottom'>
				<td class='field'><span>Credit Card First Name</span></td>
				<td class='info'><span><%= cart.getFirstName() %></span><br></td>
			</tr>
			<tr class='border_bottom'>
				<td class='field'>Credit Card Last Name</td>
				<td class='info'><span><%= cart.getLastName() %></span><br></td>
			</tr>
			<tr class='border_bottom'>
				<td class='field'>Credit Card Expiration Date</td>
				<td class='info'><span><%= cart.getDate() %></span><br></td>
			</tr>
		</table><br>
		<center><h3>Order Summary</h3></center> <!--  Change to FirstName,LastName later  -->
		<table align="center">
			<tr>
			    <th class='info'>Movie ID</th>
			    <th class='info'>Title (Year)</th>
			    <th class='info'>Price</th>
			    <th class='info'>Quantity</th>
			    <th class='info'>Subtotal</th>
			</tr>
			<%				
				for (Integer id : cart.getMovies())
				{
					Movie current = cart.getMovie(id);
					out.println("<tr>");
					out.println("<td class='info'><span>" + current.getId() + "</span></td>");
					out.println("<td class='info'><span><a href='./movie?id=" + current.getId() + "'>" + current.getTitle() + "</a> (" + current.getYear() + ")" + "</span></td>");
					out.println("<td class='info'><span>$" + current.getPrice() + "</span></td>");
					out.println("<td class='info'><span>" + current.getQuantity() + " order(s)</span></td>");
					out.println("<td class='info'>" + formatter.format(current.getQuantity() * current.getPrice()) + "</td>");
				}
			%>
		</table>
		<table align="center">
			<tr>
				<th class='info'>Total Quantity</th>
				<th class='info'>Total Price</th>
			</tr>
			<tr>
				<td class='info'><%= cart.getTotalQuantity() %></td>
				<td class='info'><%= formatter.format(cart.getTotalPrice()) %></td>
			</tr>
		</table><br>
	</div>
	<% session.removeAttribute("cart"); %>
</body>
</html>