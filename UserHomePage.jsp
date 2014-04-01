<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Home Page</title>
</head>
	<h1>This is the User Home Page</h1><br>
	<p>Welcome <c:out value = "${currentSessionUser.fName }" /> <c:out value = "${currentSessionUser.lName }" />!</p>
<div id="result">
    	<h3>${requestScope["message"]}</h3>
    </div>
<body>
	<ul>
		<li><a href="EditProfile.jsp">Edit Profile</a></li>
		<li><a href="AddItem.jsp">Add an Item</a></li>
		<li><a href="UpdateItemServlet">Update an Item</a></li>
		<li><a href="RemoveItemServlet">Remove an Item</a></li>
		<li><a href="LogoutServlet">Logout</a></li>
	</ul>
</body>
</html>