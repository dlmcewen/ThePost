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

    <!-- Bootstrap core CSS & styles -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/userHomePage.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
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
              <li><a></a></li>
              <li><a></a></li>
            </ul>
                <p class="navbar-text pull-right">
                    Logged in as <a href="UserHomePage.jsp" class="navbar-link"><c:out value = "${currentSessionUser.fName}" /> <c:out value = "${currentSessionUser.lName }"/></a>
                </p>
            </div>

      </div>
    </div>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">          
            <li><a href="index.jsp">Home</a></li>
            <li><a href="UserHomePage.jsp" class ="active">Dashboard</a></li>
          </ul>

          <ul class="nav nav-sidebar">
            <li><a href="UserHomePage.jsp">Add Item</a></li>
            <li><a href="UserHomePage.jsp#listedItems">View Your Listed Items</a></li>
            <li><a href="InitialSearchServlet?searchItem=&itemCategory=unknown">View All Items</a></li>
          </ul>

          <ul class="nav nav-sidebar">
            <li><a href="UpdateItem.jsp">Update An Item</a></li>
            <li><a href="RemoveItem.jsp">Remove An Item</a></li>
            <c:if test="${currentSessionUser.isAdmin}">
            	<li><a href="ManageUsersServlet">Manage Users (Admin Only)</a></li>
            	<li><a href="ManageItemsServlet">Manage Items (Admin Only)</a></li>
             </c:if>
          </ul>
          <ul class="nav nav-sidebar">
            <li><a href="LogoutServlet">Logout</a></li>
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header"><c:out value = "${currentSessionUser.fName}" /> <c:out value = "${currentSessionUser.lName}" />'s Dashboard</h1>
			<div id="result">
			  <c:choose>
    			 <c:when test="${requestScope['message'] == 'Password updated.'}">
           		 	<div class="alert alert-success">
              			<strong>${resetUser.fName } ${resetUser.lName }'s password was updated</strong>
            		</div>
          		 </c:when>
          		 <c:when test="${requestScope['message'] == 'Successfully Removed User'}">
           		 	<div class="alert alert-success">
              			<strong>${removeUser.fName } ${removeUser.lName } was removed</strong>
            		</div>
          		 </c:when>
          	  </c:choose>
   			 </div>
          <br>
          
      <h2 class="sub-header">All Users</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>User Name</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Manage User</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${currentSessionUser.userList}" var="user"> 
				<%-- Display an Item's image, title, date, and price --%>
        					<tr>
      		    			<td>${user.userName }</td>
      		    			<td>${user.fName }</td>
      		    			<td>${user.lName }</td>
      		    			<td>
      		    				<form action="ManageUsersServlet" method="POST">
      		    					<select name="updateSelection">
      		    						<option>--Select Option--</option>
      		    						<option value="remove">Remove User</option>
      		    						<option value="reset">Reset User Password</option>
      		    						<input type="hidden" name="userId" value="${user.id}" />
    									<input type="submit" value="Update"/>
      		    					</select>
      		    				</form>
      		    			</td>
        					</tr>
				        </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/docs.min.js"></script>
  </body>
</html>

