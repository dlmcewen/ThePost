<!-- 
- Project: ThePost
- Author: David McEwen
- Code derived from: Twitter Bootstap (http://getbootstrap.com)
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Code adapted from Twitter bootstrap (www.getBootstrap.com) -->

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>ThePost - Login or Signup</title>

    <!-- core CSS and styles -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/login.css" rel="stylesheet">
  </head>

  <body>
    <div class="container">
	<br>
      <%-- Error messages --%>
      <form class="form-signin" role="form" name="actionForm" action="LoginServlet" method ="POST">
        <c:choose>
          <c:when test="${requestScope['message'] == null}">
          </c:when>

          <c:when test="${requestScope['message'] == 'The passwords do not match.'}">
            <div class="alert alert-danger">
              <strong>The passwords do not match.</strong>
            </div>
          </c:when>

          <c:when test="${requestScope['message'] == 'Incorrect username and password, please try again.'}">
            <div class="alert alert-danger">
              <strong>Incorrect username and password, please try again.</strong>
            </div>
          </c:when>

          <c:when test="${requestScope['message'] == 'Username already exists.'}">
            <div class="alert alert-danger">
              <strong>Username already exists, please use another.</strong>
            </div>
          </c:when>

          <c:when test="${requestScope['message'] == 'Account was created, please login.'}">
            <div class="alert alert-success">
              <strong>Account was created, please login.</strong>
            </div>
          </c:when>

           <c:when test="${requestScope['message'] == 'Not a valid UGA email.'}">
            <div class="alert alert-danger">
              <strong>Not a valid UGA email.</strong>
            </div>
          </c:when>

          <c:when test="${requestScope['message'] == 'Successfully Logged Out'}">
            <div class="alert alert-success">
              <strong>You have successfully logged out!</strong>
            </div>
          </c:when>
        </c:choose>
      
        <h2 class="form-signin-heading">Login here</h2>
        <input type="email" class="form-control" placeholder="Email address" name="userName" required autofocus>
        <input type="password" class="form-control" placeholder="Password" name="password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit" value="submit">Login</button>
      </form>
    </div> <!-- /container -->

    <div class="container">
      <form class="form-signin" role="form" name="actionForm" action="CreateUserServlet" method="POST">
        
        <h2 class="form-signin-heading">Not a user? Sign up!</h2>
        <input type="text" class="form-control" placeholder="First Name" name="fName" required>
        <input type="text" class="form-control" placeholder="Last Name" name="lName" required>
        <input type="email" class="form-control" placeholder="Email address (Must be a UGA email)" name="userName" required>
        <input type="password" class="form-control" placeholder="Password" name="password" required>
        <input type="password" class="form-control" placeholder="Re-enter password" name="repassword" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
      </form>

    </div> <!-- /container -->

    <ul class="pager">
      <li><a href="index.jsp">Go Back Home</a></li>
    </ul>
  </body>
</html>