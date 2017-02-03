<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="layout_helpers.TopMenu" %>
<%@ page import="general_helpers.Movie" %>
<%@ page import="ecommerce_helpers.ShoppingCart" %>
<%@ page import="java.text.NumberFormat" %>

<%
	if (session.getAttribute("username") == null)
	{
		response.sendRedirect("./login");
	}

	ShoppingCart cart = new ShoppingCart(session);
	NumberFormat formatter = NumberFormat.getCurrencyInstance(); // Credit: http://stackoverflow.com/questions/13791409/java-format-double-value-as-dollar-amount
	int totalQuantity = 0;
	double totalPrice = 0;
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
		<span style='color: red;'><%= request.getAttribute("notice") != null ? request.getAttribute("notice") + "<br><br>" : "" %></span>
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
						out.println("<input type='hidden' name='movie_id' value='" + id + "'/>");
						out.println("<input type='number' name='quantity' min='0' value='" + current.getQuantity() + "'/>");
						out.println("<input type='submit' name='action' value='Update Quantity'/>");
						out.println("</form>");
						out.println("</td>");
						out.println("<td class='info'>");
						out.println(formatter.format(current.getQuantity() * current.getPrice()));
						out.println("</td>");
						totalQuantity += current.getQuantity();
						totalPrice += (current.getQuantity() * current.getPrice());
					}
				}
			%>
		</table>
	</div>
	<table align="center">
		<tr>
			<td>
				<table align="center">
					<tr>
						<th class='info'>Total Quantity</th>
						<th class='info'>Total Price</th>
					</tr>
					<tr>
						<td class='info'><%= totalQuantity %></td>
						<td class='info'><%= formatter.format(totalPrice) %></td>
					</tr>
				</table>			
			</td>
			<td>
				<span class='checkout'>Click here to checkout</span>
			</td>
		</tr>
	</table>
</body>
</html>