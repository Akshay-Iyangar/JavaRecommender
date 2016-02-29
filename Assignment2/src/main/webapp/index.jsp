
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style>
.queryresult
{
    display:inline-block; 
   
}
.title
{
	background:yellow;
	border-width:1px;
}
.text
{
	font-size:small;
}
.navbar{
	margin-bottom:0;
	border-radius:0;
}

</style>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand">Assignment 2</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Recommender For Java (WIKI/Oracle) contents</a></li>
      </ul>
    </div>
  </div>
</nav>
<form class="form-horizontal" name="form" role="form" method="post" action="${pageContext.request.contextPath}/Getquery">
  <div class="form-group">
    <label class="control-label col-sm-2" for="query">Dynamic Search Query</label>
    <div class="col-sm-4">
      <input type="text" name="dynamicquery" class="form-control" id="dynamicquery" class="dynamicquery" placeholder="Enter url/content">
    </div>
    <div class="col-sm-1">
      <button type="submit" value="Submit" class="btn btn-default">Submit</button>
    </div>
 <label class="control-label col-sm-2" for="static_query">Select Post(Static)</label>
    <div class="col-sm-2">
		<select name="static_query" onchange="document.form.submit();">
			<option disabled selected> -- select an option -- </option>
			<option value=1>POST 1</option>
			<option value=2>POST 2</option>
			<option value=3>POST 3</option>
			<option value=4>POST 4</option>
			<option value=5>POST 5</option>
			<option value=6>POST 6</option>
			<option value=7>POST 7</option>
			<option value=8>POST 8</option>
			<option value=9>POST 9</option>
			<option value=10>POST 10</option>
		</select>
    </div>
  </div> 
</form>
<% String query_str=(String)request.getAttribute("query_str"); %>
<div>
<%
List<String> output = (List<String>) request.getAttribute("url");
if(output!=null){
%>
<div>
<h4>The Query String is:</h4><%=query_str %>
</div>
<br>
<div><h3><center>The 10 recommended Post that you should read for this topic</center></h3></div>
<% Iterator<String> iterator = output.iterator();
		int i=0;
while (iterator.hasNext()) {%>
	<div class="title"><center><b>POST<%=++i%></b></center></div>
	<div class="queryresult"><p class="text"><%= iterator.next()%></p></div>
<%}
}
%>
</div>

</body>
</html>