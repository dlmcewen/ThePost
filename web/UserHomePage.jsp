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
    
    <script type="text/javascript">
	   
	    
	    window.onload = function() {
	    	document.getElementById('ifNew').style.display = 'none';
		}
		
	    function validateForm() {
	      var type=document.getElementById('type');
	      var t=document.getElementById('title');
	      var p=document.getElementById('price');
	      var des=document.getElementById('description');
	      var x=document.forms["addForm"]["price"].value;
	      //alert('type = ' + type.value + ' t = ' + t.value + 'p = ' + p.value + ' des = ' + des.value + ' picOriginal = ' + picOriginal + ' picNew = ' + picNew);
	      if ((type.value == 'unknown' || type.value == null) || (t.value==null || t.value=="") || (p.value==null || p.value=="") || (des.value==null || des.value=="")) {
	        alert("All fields must be filled out.");
	        return false;
	      }
	      else if (isNaN(x)){
	    		alert("Price must be a number i.e. 1534.55");
	    		return false;
	  		}
	      else{
	    	  return true;
	      }
	    }
    </script>
    
    
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
            <li><a href="#" class ="active">Dashboard</a></li>
          </ul>

          <ul class="nav nav-sidebar">

            <li><a href="#">Add Item</a></li>
            <li><a href="#listedItems">View Your Listed Items</a></li>
            <li><a href="InitialSearchServlet?searchItem=&itemCategory=unknown">View All Items</a></li>
          </ul>

          <ul class="nav nav-sidebar">
            <li><a href="UpdateItem.jsp">Update An Item</a></li>
            <li><a href="RemoveItem.jsp">Remove An Item</a></li>
            <c:if test="${currentSessionUser.isAdmin}">
            	<li><a href="ManageUsersServlet">Manage Users (Admin Only)</a></li>
            	<li><a href="ManageItems.jsp">Manage Items (Admin Only)</a></li>
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
            <c:when test="${requestScope['message'] == null}">
            </c:when>
						<c:when test="${requestScope['message'] == 'Password updated.'}">
                <div class="alert alert-success">
                  <strong>You have sucessfully updated your password!</strong>
                </div>
              </c:when>
				      <c:when test="${requestScope['message'] == 'Password updated.'}">
		            <div class="alert alert-success">
		              <strong>You have sucessfully updated your password!</strong>
		            </div>
		          </c:when>
					
		          <c:when test="${requestScope['message'] == 'Inserted object into database'}">
		            <div class="alert alert-success">
		              <strong>You have sucessfully added your listing!</strong>
		            </div>
		          </c:when>
		
		          <c:when test="${requestScope['message'] == 'Failed to insert object into database'}">
		            <div class="alert alert-danger">
		              <strong>An error occurred and your listing was not posted!</strong>
		            </div>
		          </c:when>
	       		</c:choose>
   			 </div>
          <br>
          <h2 class="sub-header">Add Item</h2>
          <form class="custom-form" name="addForm" role="form" action="AddItemServlet" method="POST" enctype="multipart/form-data" onsubmit="return validateForm()">
          
          <h5>Select Category</h5>
          <select class="form-control" name="type" id="type">
            <option value="unknown">Select Category</option>
            <option value="Book">Books</option>
            <option value="Electronic">Electronics</option>
            <option value="Furniture">Furniture</option>
            <option value="Vehicle">Vehicle</option>
            <option value="Misc">Misc</option>
          </select>

          <h5>Title</h5>
          <input type="text" placeholder="2014 Mac Pro" class="form-control" maxlength="44" name="title" id="title"></span>

          <h5>Amount</h5>
          <input type="text" placeholder="250.00" class="form-control" maxlength="13" name="price" id="price"></span>

          <h5>Description</h5>
          <textarea class="form-control" rows="4" name="description" maxlength="299" placeholder="Write a description here" id="description"></textarea>

          <div class="form-group">
            <h5>File input</h5>
            <input type="file" id="exampleInputFile" name="image">
            <p class="help-block">Please select an image to upload</p>
          </div>
          <button type="submit" value="Submit" class="btn btn-primary">Submit Listing</button>
       </form>

      <br>
      <a name="listedItems"></a>  
      <br>
      <h2 class="sub-header">Your Listed Items</h2>
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
                <c:forEach items="${currentSessionUser.itemList}" var="item"> 
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

