<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="layout_helpers.TopMenu" %>


<!DOCTYPE html>

<html>
<head>
	<meta charset="UTF-8">
	<title>Like-predicate Report</title>
	<link rel="stylesheet" type="text/css" href="../style/main.css"/> 
</head>
<body>
	<%
		TopMenu.likePredicatePrint(response.getWriter());
	%>

<H2>Submission README</H2>

<ul>
<li>For genre, we noticed that many of the genres had more than one name (i.e Sci-Fi and Science Fiction), so we decided to use 
substring matching to resolve this issue. Both "Sci" and "Fi" are substrings of both "Sci-Fi" and "Science Fiction". The like
predicate was used to find genres that had both "Sci" and "Fi" as substrings.<br/><br/></li>

<li>For star name, first_name and last_name were combined to form the star's name. We use the like predicate to lookup names 
that have the searched name as a substring. For example searching for "Jen" would provide a list of movies that have an
artist with the substring "Jen" in their name (first_name + " " + last_name).<br/><br/></li>

<li>For title, we use the like predicate to lookup titles that have the searched title as a substring. For example, searching 
for "Spy" would provide a list of movies that have a title with the substring "Spy".<br/><br/></li>

<li>For year, we use the like predicate to lookup years that have the searched year as a substring. For example, searching 
for "199" would provide a list of movies that were released in the 1990's.<br/><br/></li>

<li>For director, we use the like predicate to lookup directors that have the searched name as a substring. For example searching 
for "Mark" would provide a list of movies that have a director with the substring "Mark" in their name.<br/><br/></li>
</ul>
</body>
</html>