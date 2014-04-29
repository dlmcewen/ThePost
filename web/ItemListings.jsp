<!-- 
- Project: ThePost
- Author: Cameron Day
- Code derived from: Twitter Bootstap (http://getbootstrap.com)
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<!-- 
- Project: ThePost
- Author: Cameron Day
- Code derived from: JTable plugin(jtable.org) && Twitter Bootstap (http://getbootstrap.com)
-->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Search Page</title>


<link href="assets/css/bootstrap.min.css" rel="stylesheet">
<link href="assets/css/userHomePage.css" rel="stylesheet">

<!-- Include one of jTable styles. -->
<link href="assets/css/metro/darkgray/jtable.css" rel="stylesheet" type="text/css" />
<link href="assets/css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<!-- Include jTable script file. -->
<script src="assets/js/jquery-1.8.2.js" type="text/javascript"></script>
<script src="assets/js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="assets/js/jquery.jtable.js" type="text/javascript"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/docs.min.js">
</script><script type="text/javascript" src="/assets/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#SearchItemTable').jtable({
            title: 'Searched Items',
            paging: true,
            pageSize: 10,
            sorting: false,
            clientSort: true,
            useBootstrap: true,
            actions: {
                listAction: 'SearchServlet?action=list'
            },
            fields: {
                imageLocation: {
                 	title:'Image',
                    key: true,
                    list: true,
                    create:true,
                    display: function (data) {
                        var linkImg = '<a href="SingleItemViewServlet?itemId='+data.record.itemId+'"><img src=' + data.record.imageLocation + ' id = "picture" width="75" height="50" usemap="#map"/></a>';
                        return linkImg;
                   }
              },
                title: {
                    title: 'Item Title',
                    width: '30%',
                    edit: false,
                    display: function (data){
                    	var link = '<a href="SingleItemViewServlet?itemId='+data.record.itemId+'">'+data.record.title+'</a>';
                    	return link;
                    }
                },
                formatedDate: {
                    title: 'Date Added',
                    width: '30%',
                    edit: false
              },
                formatedPrice: {
                    title: 'Price',
                    width: '20%',
                    edit: false
                }               
            },
        });
        
        var categoryList = ["All Categories", "Book", "Electronic", "Furniture", "Vehicle", "Misc"];    
        var catSelection = document.getElementById('category');
    	var searchCategory = new String('${itemCategory}');
    	var catOpt = document.createElement('option');
    	catOpt.innerHTML = searchCategory;
    	catOpt.value = searchCategory;
    	catSelection.appendChild(catOpt);
        for(var i = 0; i < categoryList.length; i++) {
        	var txt = new String(categoryList[i]);
        	if(searchCategory.localeCompare(txt) != 0){
            	var catOpt = document.createElement('option');
            	catOpt.innerHTML = categoryList[i];
            	catOpt.value = categoryList[i];
            	catSelection.appendChild(catOpt);
        	}
        }
        $('#searchItem').val($('#searchItem').val() + '${searchItem}');
        
        var sortList=["Date: Newest", "Date: Oldest", "Price: Highest First", "Price: Lowest First"];
        var sortSelection = document.getElementById('sorting');
    	var sortCategory = new String('${sortingCat}');
    	var sortOpt = document.createElement('option');
    	sortOpt.innerHTML = sortCategory;
    	sortOpt.value = sortCategory;
    	sortSelection.appendChild(sortOpt);
        for(var i = 0; i < sortList.length; i++) {
        	var txt = new String(sortList[i]);
        	if(sortCategory.localeCompare(txt) != 0){
            	var sortOpt = document.createElement('option');
            	sortOpt.innerHTML = sortList[i];
            	sortOpt.value = sortList[i];
            	sortSelection.appendChild(sortOpt);
        	}
        }
     
        
        $('#SearchButton').click(function (e) {
            e.preventDefault();
            $('#SearchItemTable').jtable('load', {
                searchString: $('#searchItem').val(),
                category: $('#category').val()
            });
            document.getElementById("lowPrice").value="";
            document.getElementById("highPrice").value="";
            $('#hasPic').attr('checked',false);
        });
        
        $('#Sort').click(function (e) {
        	e.preventDefault();
        	$('#SearchItemTable').jtable('load', {
            	searchString: $('#searchItem').val(),
            	category: $('#category').val(),
            	sorting: $('#sorting').val(),
        	});
        	document.getElementById("lowPrice").value="";
            document.getElementById("highPrice").value="";
            $('#hasPic').attr('checked',false);
        });
        
        $('#ModifyPrice').click(function (e) {
        	e.preventDefault();
            var picOption = "no";
        	if ( $('#hasPic').is(':checked') ){
            	picOption="yes";
            }
            $('#SearchItemTable').jtable('load', {
                searchString: $('#searchItem').val(),
                category: $('#category').val(),
                lowPrice: $('#lowPrice').val(),
                highPrice: $('#highPrice').val(),
            	sorting: $('#sorting').val(),
            	hasPic: picOption,
            });
            document.getElementById("lowPrice").value="";
            document.getElementById("highPrice").value="";
            $('#hasPic').attr('checked',false);
        });
 
        //displays items on page load
        $('#SearchButton').click();
        
    });
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
          <ul class="nav navbar-nav">
            <c:choose>
                  <c:when test="${currentSessionUser.id == -1}">
                      <li><a href="index.jsp">Home</a></li>
                  </c:when>
                  <c:when test="${currentSessionUser.id != null}">
                      <li><a href="UserHomePage.jsp">Dashboard</a></li>
                      <li><a href="UserHomePage.jsp">Add Item</a></li>
                  </c:when>
                  <c:when test="${currentSessionUser.id == null}">
                    <li class="active"><a href="index.jsp">Home</a></li>
                  </c:when>
              </c:choose>
          </ul>
          	
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
                          <li><a href="LogoutServlet">Logout</a></li>
                        </ul>
                      </li>
                      <li><a></a></li>
                      <li><a></a></li>
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


<!--
          <ul class="nav navbar-nav pull-right">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="index.jsp">Home</a></li>
                  <li><a href="EditProfile.jsp">Edit Profile</a></li>
                  <li class="divider"></li>
                  <li><a href="LogoutServlet">Logout</a></li>
                </ul>
              </li>
              <li><a></a></li>
              <li><a></a></li>
            </ul>
                <p class="navbar-text pull-right">
					<c:choose>
                  <c:when test="${currentSessionUser.id == -1}">
                      Please <a href="Login.jsp" class="navbar-link">Login</a>
                      | Or <a href="Login.jsp" class="navbar-link">Sign-Up</a>
                    </c:when>
                    <c:when test="${currentSessionUser.id != null}">
                      Logged in as <a href="UserHomePage.jsp" class="navbar-link"><c:out value = "${currentSessionUser.fName }"/> <c:out value = "${currentSessionUser.lName }"/></a>
                    </c:when>
                    <c:when test="${currentSessionUser.id == null}">
                      Please <a href="Login.jsp" class="navbar-link">Login</a>
                      | Or <a href="Login.jsp" class="navbar-link">Sign-Up</a>
                    </c:when>
                </c:choose>
                </p>
            </div>
          -->

      </div>
    </div>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
        
        	<h4 style="text-decoration: underline">Modify Search</h4>
        	<h5>Price</h5>
        	<form action="SearchServlet" method="post">
        		$<input type="text" name="lowPrice" id="lowPrice" placeholder="Min" style="width:30%" />
        		to $<input type="text" name="highPrice" id="highPrice" placeholder="Max" style="width:30%" />
        	</form>
        	<br>
        	<form action="SearchServlet" method="post">
        		<h5>Has Image: <input type="checkbox" name="hasPic" value="hasPicValue" id="hasPic" /></h5>
        	</form>
        	<button type="button" id="ModifyPrice" class="btn btn-default" style="padding: 3px 3px 3px 3px; vertical-align: middle">
      				Modify <span class="glyphicon glyphicon-chevron-right" style="font-size:70%"></span>
   			</button>
   			<br><br>
          <!-- <ul class="nav nav-sidebar">     
              
            <li><a href="index.jsp">Home</a></li>
            <li><a href="#" class ="active">Dashboard</a></li>
          </ul>
        -->

        </div>
   	</div>
   </div>
   
   <br>

  <div style="margin-right:5%; margin-left:20%;">
    <h3>Search For An Item</h3>
      <form class="form-inline" action="SearchServlet" method="get" role="form">
        <div class="form-group">
          <input style="width:300px" type="text" class="form-control" id="searchItem" placeholder="Search...">
        </div>
        <div class="form-group">
          <select style="width:160px" class="form-control" id="category" name="cateogry">
          </select>
        </div>
        <button type="submit" id="SearchButton" class="btn btn-primary">Search</button>
      </form>
  </div>

<div style="margin-right:5%; margin-left:20%;">
  <h3>Sort By</h3>
    <form class="form-inline" action="SearchServlet" method="get" role="form">
      <div class="form-group">
        <select style="width:240px" id="sorting" class="form-control" name="sorting">
        </select>
      </div>
      <button type="submit" id="Sort" class="btn btn-primary">Sort</button>
    </form>
</div>

<!--

      <div style="margin-right:5%; margin-left:20%;">
    	<form action="SearchServlet" method="get">
        	<input type="text" name="searchItem" id="searchItem" placeholder="Search..." style="width:45%"/>
        	Category: 
        	<select id="category" name="cateogry">
        	</select>
        	<button type="submit" id="SearchButton" class="btn btn-default">Search</button>
    	</form>
	</div>
	<br>
	
	<div style="margin-right:5%; margin-left:54%;">
    	<form action="SearchServlet" method="get">
        	Sort By:
        	<select id="sorting" name="sorting">
        	</select>
        	<button type="submit" id="Sort" class="btn btn-default">Sort</button>
    	</form>
	</div>
-->
	<br><br>

	<div style="width:80%;margin-right:5%;margin-left:20%;text-align:center;">
	<div id="SearchItemTable"></div>
	</div>
	
	
</body>
</html>
