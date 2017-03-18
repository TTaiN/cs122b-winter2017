<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="layout_helpers.TopMenu" %>


<!DOCTYPE html>
<html>
<head>
<style>
body {
    background-color: linen;
}

td {
    border-top-style: solid;
}
</style>
</head>
<body>

<table style="width:100%">
  <tr style="font-weight:bold; background-color: orange">
    <td width="300px">Single-instance version cases</td>
    <td>Graph Results Screenshot</td>
    <td>Average Query Time(ms)</td>
    <td>Average Search Servlet Time(ms)</td>
    <td>Average JDBC Time(ms)</td>
    <td>Analysis</td>
  </tr>
  <tr>
    <td>Case 1: HTTP/1 thread</td>
    <td><img src="./jmeter-images/1-1.png" alt="Graph Results Screenshot Case 1" style="width:304px;height:228px;"></td>
    <td>??</td>
    <td>??</td>
    <td>??</td>
    <td>--</td>
  </tr>
  <tr>
    <td>Case 2: HTTP/10 threads</td>
    <td><img src="./jmeter-images/1-2.png" alt="Graph Results Screenshot Case 2" style="width:304px;height:228px;"></td>
    <td>??</td>
    <td>??</td>
    <td>??</td>
    <td>--</td>
  </tr>
  <tr>
    <td>Case 3: HTTPS/10 threads</td>
    <td><img src="./jmeter-images/1-3.png" alt="Graph Results Screenshot Case 3" style="width:304px;height:228px;"></td>
    <td>??</td>
    <td>??</td>
    <td>??</td>
    <td>--</td>
  </tr>
  <tr>
    <td>Case 4: HTTP/10 threads/No prepared statements</td>
    <td><img src="./jmeter-images/1-4.png" alt="Graph Results Screenshot Case 4" style="width:304px;height:228px;"></td>
    <td>??</td>
    <td>??</td>
    <td>??</td>
    <td>--</td>
  </tr>
  <tr>
    <td>Case 5: HTTP/10 threads/No connection pooling</td>
    <td><img src="./jmeter-images/1-5.png" alt="Graph Results Screenshot Case 5" style="width:304px;height:228px;"></td>
    <td>??</td>
    <td>??</td>
    <td>??</td>
    <td>--</td>
  </tr>

</table> 


<table style="width:100%">
  <tr style="font-weight:bold; background-color: orange">
    <td width="300px">Scaled version cases</td>
    <td>Graph Results Screenshot</td>
    <td>Average Query Time(ms)</td>
    <td>Average Search Servlet Time(ms)</td>
    <td>Average JDBC Time(ms)</td>
    <td>Analysis</td>
  </tr>
  <tr>
    <td>Case 1: HTTP/1 thread</td>
    <td><img src="./jmeter-images/2-1.png" alt="Graph Results Screenshot Case 1" style="width:304px;height:228px;"></td>
    <td>??</td>
    <td>??</td>
    <td>??</td>
    <td>--</td>
  </tr>
  <tr>
    <td>Case 2: HTTP/10 threads</td>
    <td><img src="./jmeter-images/2-2.png" alt="Graph Results Screenshot Case 2" style="width:304px;height:228px;"></td>
    <td>??</td>
    <td>??</td>
    <td>??</td>
    <td>--</td>
  </tr>
  <tr>
    <td>Case 3: HTTP/10 threads/No prepared statements</td>
    <td><img src="./jmeter-images/2-3.png" alt="Graph Results Screenshot Case 3" style="width:304px;height:228px;"></td>
    <td>??</td>
    <td>??</td>
    <td>??</td>
    <td>--</td>
  </tr>
  <tr>
    <td>Case 4: HTTP/10 threads/No connection pooling</td>
    <td><img src="./jmeter-images/2-4.png" alt="Graph Results Screenshot Case 4" style="width:304px;height:228px;"></td>
    <td>??</td>
    <td>??</td>
    <td>??</td>
    <td>--</td>
  </tr>

</table> 

</body>
</html>
