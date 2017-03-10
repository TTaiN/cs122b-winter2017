<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="layout_helpers.TopMenu" %>

<%
	if (session.getAttribute("username") == null || request.getAttribute("jsp") == null)
	{
		response.sendRedirect("../login");
		return;
	}
%>

<!DOCTYPE html>
<html>
<head>
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script type="text/javascript">
		function showResult(str) {
	
			$.get("./livesearch", {q:str}, function(data){
			$("#results").html(data);
			});
		}
	</script>
	<title>Search Page</title>
	<link rel="stylesheet" type="text/css" href="./style/main.css"/> 
	<link rel="stylesheet" type="text/css" href="./style/search.css"/> 
	<link rel="stylesheet" type="text/css" href="./style/livesearch.css"/> 

</head>
<body>
	<%
		TopMenu.print(response.getWriter());
	%>
	<h1 ALIGN="CENTER">Search:</h1>
	<form ACTION="./movielist" METHOD="GET">
  		<input type="text" name="title" placeholder="Movie Title.."><br><br>
  		<input type="text" name="year" placeholder="Year.." maxlength=4 onkeyup="this.value=this.value.replace(/[^\d]/,'')"><br><br>
  		<input type="text" name="director" placeholder="Director.."><br><br>
  		<input type="text" name="star" placeholder="Star's Name.."><br><br>
  		<input type="submit" value="Submit"><br>
	</form>
	<br>
</body>
</html>