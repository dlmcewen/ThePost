<!-- 
- Project: ThePost
- Author: David McEwen
- Code derived from: Twitter Bootstap (http://getbootstrap.com)
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ThePost</title>

    <!-- core CSS and styles -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/singleItemView.css" rel="stylesheet">
    
    <!-- http://www.w3schools.com/jsref/met_his_back.asp -->
    <script>
      function goBack() {
        window.history.back()
      }
    </script>
  </head>

  <body>

    <div class="container">

      <!-- Static navbar -->
      <div class="navbar navbar-inverse navbar-default" role="navigation">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">ThePost</a>
          </div>
         <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
             <c:choose>
                  <c:when test="${currentSessionUser.id == -1}">
                      <li><a href="index.jsp">Home</a></li>
                      <li><a href="About.jsp">About</a></li>
                      <li><a href="Contact.jsp">Contact Us</a></li>
                  </c:when>
                  <c:when test="${currentSessionUser.id != null}">
                      <li><a href="UserHomePage.jsp">Dashboard</a></li>
                      <c:if test="${currentSessionUser.isAdmin == true }">
                      	<li><a href="AdminUpdateItemServlet?itemId=${singleItemView.itemId}">Update This Item</a>
                      	<li><a href="AdminRemoveItemServlet?itemId=${singleItemView.itemId}">Remove This Item</a>
                      </c:if>
                  </c:when>
                  <c:when test="${currentSessionUser.id == null}">
                    <li><a href="index.jsp">Home</a></li>
                    <li><a href="About.jsp">About</a></li>
                    <li><a href="Contact.jsp">Contact Us</a></li>
                  </c:when>
              </c:choose>

              <!-- Adds a drop down navigation -->
              <!--
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Action</a></li>
                  <li><a href="#">Another action</a></li>
                  <li class="divider"></li>
                  <li class="dropdown-header">Nav header</li>
                  <li><a href="#">Separated link</a></li>
                  <li><a href="#">Another separated link</a></li>
                </ul>
              </li>
            -->
            </ul>
              <div class="navbar-collapse collapse">
                <c:choose>
                  <c:when test="${currentSessionUser.id == -1}">
                    <p class="navbar-text pull-right">
                        Please <a href="Login.jsp" class="navbar-link">Login</a>
                        | Or <a href="Login.jsp" class="navbar-link">Sign-Up</a>
                    </p>
                  </c:when>
                  <c:when test="${currentSessionUser.id != null}">
                    <ul class="nav navbar-nav pull-right">
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b class="caret"></b></a>
                        <ul class="dropdown-menu">
                          <li><a href="index.jsp">Home</a></li>
                          <li><a href="EditProfile.jsp">Edit Profile</a></li>
                          <li class="divider"></li>
                          <!--<li class="dropdown-header">Nav header</li>-->
                          <li><a href="LogoutServlet">Logout</a></li>
                        </ul>
                      </li>
                    </ul>
                    <p class="navbar-text pull-right">
                      Logged in as <a href="UserHomePage.jsp" class="navbar-link"><c:out value = "${currentSessionUser.fName }"/> <c:out value = "${currentSessionUser.lName }"/></a>
                    </p>
                  </c:when>
                  <c:when test="${currentSessionUser.id == null}">
                    <p class="navbar-text pull-right">
                      Please <a href="Login.jsp" class="navbar-link">Login</a>
                      | Or <a href="Login.jsp" class="navbar-link">Sign-Up</a>
                    </p>
                  </c:when>
              </c:choose>
            </div>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </div>

      <h1>${singleItemView.title}</h1>
      <img src="${singleItemView.imageLocation}" width="200px" height="200px"><br><br>

      <h3 style="display:inline">Price: </h3>
      <p style="display:inline">${singleItemView.formatedPrice}</p>

      <h4>Description</h4>
      <p style="width:380px">${singleItemView.description}</p>

      <br>
      <h3 style="display:inline">User's Email: </h3>
  			<c:choose>
          <c:when test="${currentSessionUser.id == -1}">
              To see the user's email, please <a href="Login.jsp" class="navbar-link">Login</a>, or <a href="Login.jsp" class="navbar-link">Sign-Up</a>
            </c:when>
            <c:when test="${currentSessionUser.id != null}">
              <a href="mailto:${requestScope['email']}" target="_top">${requestScope["email"]}</a><br>
            </c:when>
            <c:when test="${currentSessionUser.id == null}">
              To see the user's email, please <a href="Login.jsp" class="navbar-link">Login</a>, or <a href="Login.jsp" class="navbar-link">Sign-Up</a>
            </c:when>
        </c:choose>
     	<br>
		<br>
      <button onclick="goBack()" class="btn btn-default">Go Back</button>

    </div> <!-- /container -->

    <div class="post-footer">
      <p>&copy; ThePost - 2014</p>
      <p>This site is powered by <a href="http://getbootstrap.com">Twitter Bootstrap</a>.</p>
      <p><a href="#">Back to top</a></p>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
  </body>
</html>
