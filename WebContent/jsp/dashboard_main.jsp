<%@ page import="layout_helpers.TopMenu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./style/main.css"/> 
<title>Dashboard</title>
</head>
<body>

	<%
		TopMenu.dashboardPrint(response.getWriter());
	%>
	    	
<center>
<H1>Dashboard</H1>
<% 
if (request.getAttribute("notice") != null) {
	if (request.getAttribute("notice").equals("Movie inserted") || request.getAttribute("notice").equals("Star inserted")) {
		out.println("<center><p style='color:green;'>" + request.getAttribute("notice") + "</p></center>");
	} else {
		out.println("<center><p style='color:red;'>" + request.getAttribute("notice") + "</p></center>");
	}
}
%>

<H3>1: Insert new star</H3>
<form ACTION="./dashboardInsertStar" METHOD="POST">
  		Star ID <input type="text" name="id" placeholder="Required.."><br><br>
  		First Name <input type="text" name="firstName" placeholder="Blank if one name.."><br><br>
  		Last Name <input type="text" name="lastName" placeholder="Required.."><br><br>
  		Date of Birth <input type="date" name="dob" placeholder="YYYY-MM-DD"><br><br>
  		Photo URL <input type="text" name="photoUrl"><br><br>
  		<input type="submit" value="Submit"><br>
</form>


<H3>2: Print metadata</H3>

<form action="./dashboardMain" method="GET">
	<center><input type="submit" name="metadata" value="Print Metadata"/><br></center>
</form>	

<H3>3: Add movie</H3>
<form ACTION="./dashboardAddMovie" METHOD="POST">
		<p>Movie Information</p>
  		Movie ID <input type="text" name="movieId" placeholder="Required.."><br><br>
  		Movie Title <input type="text" name="title" placeholder="Required.."><br><br>
  		Year <input type="text" name="year" placeholder="Required.."><br><br>
  		Director <input type="text" name="director" placeholder="Required.."><br><br>
  		Banner URL <input type="text" name="bannerUrl"><br><br>
  		Trailer URL <input type="text" name="trailerUrl"><br><br>
  		<p>Star Information (if exists only put the name)</p>
  		Star ID <input type="text" name="starId"><br><br>
  		First Name <input type="text" name="firstName" placeholder="Blank if one name.."><br><br>
  		Last Name <input type="text" name="lastName"><br><br>
  		Date of Birth <input type="date" name="dob" placeholder="YYYY-MM-DD"><br><br>
  		Photo URL <input type="text" name="photoUrl"><br><br>
  		<p>Genre Information (if exists only put the genre name)</p>
  		Genre ID <input type="text" name="genreId"><br><br>
  		Genre Name <input type="text" name="genreName"><br><br>
  		<input type="submit" value="Submit"><br>
</form>
	
</center>

</body>
</html>