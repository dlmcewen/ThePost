<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create an Account</title>
</head>
<body>

	<div id="result">
    	<h3>${requestScope["message"]}</h3>
    </div>

	<form name="actionForm" action="CreateUserServlet" method ="POST">
		<table>
			<tr><td>Enter your First Name: </td><td><input type="text" name="fName"/></td></tr>
			<tr><td>Enter your Last Name: </td><td><input type="text" name="lName"/></td></tr>
			<tr><td>Enter your email: </td><td><input type="text" name="userName"/></td></tr>
			<tr><td>Enter your Password: </td><td><input type="password" name="password"/></td></tr>
			<tr><td colspan="2" align="center"><input type="submit" value="submit"> </td></tr>
		</table>
	</form>
</body>
</html>