<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add an Item</title>
</head>
<body>
	<h1>Post an item to sell</h1>
	
	<form name = "setType" action = "AddItemServlet" method = "POST" enctype="multipart/form-data">
	<table>
	<tr><td>
		<select name = "itemType">
		  <option value="unknown">--Select Type--</option>
		  <option value="Book">Book</option>
		  <option value="Electronic">Electronic</option>
		  <option value="Furniture">Furniture</option>
		  <option value="Vehicle">Vehicle</option>
		  <option value="Misc">Misc</option>
		</select>
	</td></tr>
	<tr><td>
		Enter a Price (i.e 15.00): <input type="text" name="price"/>
	</td></tr>
	<tr><td>
		<textarea name="description" id="term" style="width: 400px; height: 400px;">Delete me and write a description.</textarea>
	</td></tr>
	<tr><td>
		Please select an image to upload:<input type="file" name="image">
	</td></tr>
	</table>
	<input type="submit" value="Submit"/>
	</form>
	<br>
	<a href="UserHomePage.jsp">Back</a><br><br>
	<a href="LogoutServlet">Logout</a>
</body>
</html>