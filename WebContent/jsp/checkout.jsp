<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="general_helpers.Movie" %>
<%@ page import="ecommerce_helpers.ShoppingCart" %>
<%@ page import="java.text.NumberFormat" %>

<%
	ShoppingCart cart = new ShoppingCart(session);

	if (session.getAttribute("username") == null || request.getAttribute("jsp") == null || (cart.isEmpty()))
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
	<title>Cart</title>
</head>
<body>
	<%
		TopMenu.print(response.getWriter());
	%>

	<div class="focus">
		<h1>Checkout</h1>
		<p>${sessionScope.username}'s Order Summary</p> <!--  Change to FirstName,LastName later  -->
		<span style='color: red;'><%= request.getAttribute("notice") != null ? request.getAttribute("notice") + "<br><br>" : "" %></span>
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
		</table><br>
		<table align="center">
			<tr>
				<th class='info'>Total Quantity</th>
				<th class='info'>Total Price</th>
			</tr>
			<tr>
				<td class='info'><%= cart.getTotalQuantity() %></td>
				<td class='info'><%= formatter.format(cart.getTotalPrice()) %></td>
			</tr>
		</table>
		<h3>Customer Information</h3>
		<form action="./checkout" method="POST">
			Credit Card Number: <input type="text" name="number"/><br>
			Credit Card First Name: <input type="text" name="firstName"/><br>
			Credit Card Last Name: <input type="text" name="lastName"/><br>
			Credit Card Expiration Date: <input type="date" name="date" name="firstName"/><br>
			<center><input type="submit" name="action" value="Submit Order"/><br></center>
		</form>		
	</div>
</body>
</html>