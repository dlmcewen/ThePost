<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1><c:out value = "${currentSessionUser.fName }" />'s Items</h1>
	
	<table>
		<tr>
			<th>Item ID</th>
			<th>Item Type</th>
			<th>Item Price</th>
			<th>Item Description</th>
			<th>Item Image</th>
		</tr>
		<c:forEach items="${currentSessionUser.itemList}" var="item"> 
		<%-- Display a student's last name, first name, email and photo--%>
  			<tr>
    			<td>${item.itemId}</td>
    			<td>${item.type}</td>
    			<td>${item.price}</td>
    			<td>${item.description}</td>
    			<td><img src="${item.imageLocation}" style="float:right" width="100" height="100" alt=""></td>
  			</tr>
		</c:forEach>
	</table>
	
	<br>
	<a href="UserHomePage.jsp">Back</a><br><br>
	<a href="LogoutServlet">Logout</a>
	
</body>
</html>