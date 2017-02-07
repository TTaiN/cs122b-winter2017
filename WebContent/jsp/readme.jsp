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
<li><br/><br/></li>

<li>(Note: We assume you are using Eclipse.) How to compile java sources: place all .java and package folders into /src folder for Java resources. Ensure that you add
JDBC jar to your Java build path, since some classes require it.<br/><br/></li>

<li>To link the WebContent, place all contents inside the WebContent folder into the web root folder for your project. If
the name of your web root folder is WebContent, simply replace WebContent folder with the one provided.<br/><br/></li>

<li>The database credentials are: (IP - 35.167.240.46) (User - cs122b) (Password - cs122bgroup42) (DB - moviedb)<br/><br/></li>

<li>To create the .war, after following above instructions export the classes and sources as a .war.<br/><br/></li>


<li>To deploy the .war, login to Tomcat Application Manager, select the .war provided and deploy it.<br/><br/></li>
</ul>
</body>
</html>