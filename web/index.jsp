<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean class = "thepost.ListBean" id ="listBean"/>
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
    <link href="assets/css/index.css" rel="stylesheet">
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
                      <li class="active"><a href="index.jsp">Home</a></li>
                      <li><a href="About.jsp">About</a></li>
                      <li><a href="Contact.jsp">Contact Us</a></li>
                  </c:when>
                  <c:when test="${currentSessionUser.id != null}">
                      <li><a href="UserHomePage.jsp">Dashboard</a></li>
                      <li><a href="About.jsp">About</a></li>
                      <li><a href="Contact.jsp">Contact Us</a></li>
                  </c:when>
                  <c:when test="${currentSessionUser.id == null}">
                    <li class="active"><a href="index.jsp">Home</a></li>
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

      <c:choose>
      <c:when test="${currentSessionUser.id == -1}">
          <h1>Welcome!</h1>
        </c:when>
        <c:when test="${currentSessionUser.id != null}">
          <h1>Welcome <c:out value = "${currentSessionUser.fName}"/> <c:out value = "${currentSessionUser.lName }"/>!</h1>
        </c:when>
        <c:when test="${currentSessionUser.id == null}">
          <h1>Welcome!</h1>
        </c:when>
      </c:choose>
      <!-- Change for jsp to ^ -->
      <p>A site for UGA students to buy and sell used products!</p>

      <br>

      <h3>Search for an item</h3>
       <form class="custom-form" role="form" action="InitialSearchServlet" method="get">
              <input type="text" placeholder="Laptop, Couch, etc..." class="form-control" name="searchItem">
              <select class="form-control" name="itemCategory">
                <option value="unknown">Select Category</option>
                <option value="All Categories">All Categories</option>
                <option value="Book">Books</option>
                <option value="Electronic">Electronics</option>
                <option value="Furniture">Furniture</option>
                <option value="Housing">Housing</option>
                <option value="Vehicle">Vehicle</option>
                <option value="Misc">Misc</option>
              </select>
            <button type="submit" class="btn btn-primary">Search</button>
       </form>

      <h2 class="sub-header">Recently Listed Items</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>Image</th>
                  <th>Item Title</th>
                  <th>Date of last Update</th>
                  <th>Price</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${listBean.itemList}" var="item"> 
        <%-- Display an Item's image, title, date, and price --%>
                  <tr>
                    <td><a href="SingleItemViewServlet?itemId=${item.itemId}"><img src="${item.imageLocation}" width="100" height="100"></a></td>
                    <td><a href="SingleItemViewServlet?itemId=${item.itemId}">${item.title}</a></td>
                    <td>${item.formatedDate}</td>
                    <td>${item.formatedPrice}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
            <ul class="pager">
              <li><a href="InitialSearchServlet?searchItem=&itemCategory=unknown">View All Items</a></li>
            </ul>


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
    <script src="assets/js/docs.min.js"></script>
  </body>
</html>
