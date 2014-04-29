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
      var picOriginal=document.getElementById('original').checked;
      var picNew=document.getElementById('new').checked;
      var x=document.forms["update"]["price"].value;
      //alert('type = ' + type.value + ' t = ' + t.value + 'p = ' + p.value + ' des = ' + des.value + ' picOriginal = ' + picOriginal + ' picNew = ' + picNew);
      if ((type.value == 'unknown' || type.value == null) || (t.value==null || t.value=="") || (p.value==null || p.value=="") || (des.value==null || des.value=="") || (picOriginal == false && picNew == false)) {
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
    
    function yesnoCheck() {
    	if (document.getElementById('new').checked) {
    	    document.getElementById('ifNew').style.display = 'block';
    	} else {
    	    document.getElementById('ifNew').style.display = 'none';
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
            <li><a href="UserHomePage.jsp" class ="active">Dashboard</a></li>
          </ul>

          <ul class="nav nav-sidebar">

            <li><a href="UserHomePage.jsp">Add Item</a></li>
            <li><a href="UserHomePage.jsp">View Listed Items</a></li>
          </ul>
          
          <ul class="nav nav-sidebar">
            <li><a href="UpdateItem.jsp">Update Item</a></li>
            <li><a href="RemoveItem.jsp">Remove An Item</a></li>
          </ul>
          <ul class="nav nav-sidebar">
            <li><a href="LogoutServlet">Logout</a></li>
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header"><c:out value = "${currentSessionUser.fName}" /> <c:out value = "${currentSessionUser.lName}"/>'s Dashboard</h1>

          <br>
          <a name="editProfile"></a>

          <c:choose>
          <c:when test="${requestScope['message'] == null}">
          </c:when>

          <c:when test="${requestScope['message'] == 'Updated object in database'}">
            <div class="alert alert-success">
              <strong>You have sucessfully updated the listing!</strong>
            </div>
          </c:when>

          <c:when test="${requestScope['message'] != 'Updated object in database'}">
            <div class="alert alert-danger">
              <strong>Update listing failed!</strong>
            </div>
          </c:when>
        </c:choose>


          <c:choose>
          	<c:when test="${adminUpdateItem == null }"> 
         		 <h2 class="sub-header">Select An Item To Update</h2>
         	</c:when>
         	<c:when test="${adminUpdateItem != null }">
         		<h2 class="sub-header">Update ${userNameUpdate} item: ${adminUpdateItem.title}</h2>
         	</c:when>
         </c:choose>
          
          <!-- foreach loop that does the table -->


          <!-- in foreach item.id-->
          <br>
          
          <c:choose>
			<c:when test="${adminUpdateItem != null}">
          
		          <form name="update" class="custom-form" role="form" action="AdminUpdateItemServlet" method="POST" enctype="multipart/form-data" onsubmit="return validateForm()">
		          
		          <h5>Change Category</h5>
		          <select class="form-control" name="type" id="type">
		            <option value="${adminUpdateItem.type }">Current Type (${adminUpdateItem.type })</option>
		            <option value="Book">Books</option>
		            <option value="Electronic">Electronics</option>
		            <option value="Furniture">Furniture</option>
		            <option value="Vehicle">Vehicle</option>
		            <option value="Misc">Misc</option>
		          </select>
					
		          <h5>Change Title</h5>
		          <input type="text" value="${adminUpdateItem.title}" class="form-control" name="title" id="title"/>
		
		          <h5>Change Amount</h5>
		          <input type="text" value="${adminUpdateItem.price}" class="form-control" name="price" id="price"/>
		
		          <h5>Change Description</h5>
		          <textarea rows="4" class="form-control" name="description" id="description">${adminUpdateItem.description}</textarea>
				  <!-- 
				  <br>
				  <input type="radio" name="picture" id="original" value="original">Keep Original Picture<br>
				  <input type="radio" name="picture" id="new" value="new">Upload New Picture<br>	
				  <br>
				  -->
				    <input type="radio" onclick="yesnoCheck()" name="picture" id="original" value="original"/>Keep Original Picture<br>
					<input type="radio" onclick="yesnoCheck()" name="picture" id="new" value="new"/>Upload New Picture<br>
					<br>			  

			          <div class="form-group" id="ifNew" style="visibility:none">
			            <h5>Change File</h5>
			            <input type="file" value="fileName" name="image" />
			            <p class="help-block">Please re-select an image to upload</p>
			          </div>
		
		          <input type="hidden" name="itemId" value="${adminUpdateItem.itemId}"/>
		
		          <button type="submit" value="Submit" class="btn btn-primary">Update Listing</button>
		       </form>
		     </c:when>
		    </c:choose>

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

