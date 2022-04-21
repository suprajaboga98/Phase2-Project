<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*" %>
<%ResultSet resultset =null;%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Flight Page</title>
</head>
<body>
 	<h2>Add Flight</h2>
 	<form action="add-flight" method="POST">
		<%
	       Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/flyaway?user=root&password=password");
	       Statement statement = connection.createStatement() ;
	       resultset =statement.executeQuery("select airline from airline") ;
	     %>
	     <p>
	        <label for="name">Airline</label>
	        <input type="text" name="airlinename"/>
	        <select>
	        <%  while(resultset.next()){ %>
	            <option> <%= resultset.getString(1)%></option>
	         <% } %>
	        </select>
	      </p>
	      Flight Id: <input type="number" name="flightid"> <br/><br/>
		  No.of Seats: <input type="number" name="noofseats"> <br/><br/>
		  Source: <input type="text" name="source"> <br/><br/>
		  Destination: <input type="text" name="destination"> <br/><br/>
		  Date of Departure: <input type="date" name="departuredate"> <br/><br/>
		  Date of Arrival: <input type="date" name="arrivaldate"> <br/><br/>
		  Price: <input type="number" name="price"> <br/><br/>
		  <input type="submit" value="Add">
	</form>	
</body>
</html>